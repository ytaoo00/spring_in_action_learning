//tag::recents[]
//A Degisn Taco Controller(REST controller)
package tacos.web.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//end::recents[]
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//tag::recents[]
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tacos.Taco;
import tacos.data.TacoRepository;

@RestController // this is a stereotype annotation like @Controller
//the @RestController tells Spring that all handler methods in the controller should have 
//their returnvalue written directly to the body of the response, 
//rather than being carried in the model to a view for rendering.
@RequestMapping(path="/design",                      // <1>
                produces="application/json") //This specifies that any of the handler methods in DesignTacoController will only
//handle requests if the request’s Accept header includes “application/json”.
//this limit your API to only producing JSON results
//and it also allows for another controller (perhaps the DesignTacoController from chapter 2) 
//to handle requests with the same paths, so long as those requests don’t require JSON output
@CrossOrigin(origins="*")        // <2>
public class DesignTacoController {
  private TacoRepository tacoRepo;
  
  @Autowired
  EntityLinks entityLinks;

  public DesignTacoController(TacoRepository tacoRepo) {
    this.tacoRepo = tacoRepo;
  }

  //OK so this is an API
  //if you Send GET /design/recent
  //get back a json object which contains at most 12 most recent taco design

  @GetMapping("/recent") //is responsible for handling GET requests for /design/recent (which is exactly what your Angular code needs).
  public Iterable<Taco> recentTacos() {                 //<3>
	  //constructs a PageRequest object that specifies that you only want the first (0th) page of 12 results,
	  //sorted in descending order by the taco’s creation date
	  PageRequest page = PageRequest.of(
            0, 12, Sort.by("createdAt").descending());
    return tacoRepo.findAll(page).getContent();
  }
  //end::recents[]

//  @GetMapping("/recenth")
//  public Resources<TacoResource> recentTacosH() {
//    PageRequest page = PageRequest.of(
//            0, 12, Sort.by("createdAt").descending());
//    List<Taco> tacos = tacoRepo.findAll(page).getContent();
//    
//    List<TacoResource> tacoResources = 
//        new TacoResourceAssembler().toResources(tacos);
//    Resources<TacoResource> recentResources = 
//        new Resources<TacoResource>(tacoResources);
//    recentResources.add(
//        linkTo(methodOn(DesignTacoController.class).recentTacos())
//        .withRel("recents"));
//    return recentResources;
//  }

  
  
//ControllerLinkBuilder.linkTo(DesignTacoController.class)
//.slash("recent")
//.withRel("recents"));

  
  
  
//  @GetMapping("/recenth")
//  public Resources<TacoResource> recenthTacos() {
//    PageRequest page = PageRequest.of(
//            0, 12, Sort.by("createdAt").descending());
//    List<Taco> tacos = tacoRepo.findAll(page).getContent();
//
//    List<TacoResource> tacoResources = new TacoResourceAssembler().toResources(tacos);
//    
//    Resources<TacoResource> tacosResources = new Resources<>(tacoResources);
////    Link recentsLink = ControllerLinkBuilder
////        .linkTo(DesignTacoController.class)
////        .slash("recent")
////        .withRel("recents");
//
//    Link recentsLink = 
//        linkTo(methodOn(DesignTacoController.class).recentTacos())
//        .withRel("recents");
//    tacosResources.add(recentsLink);
//    return tacosResources;
//  }
  
  //tag::postTaco[]
  @PostMapping(consumes="application/json") //not specifying a path attribute here, so the postTaco() method will handle post requests for /design
  //Here you use consumes to say that the method will only handle requests whose Content-type matches application/json .
  @ResponseStatus(HttpStatus.CREATED) //In the case of a POST request, an HTTP status of 201 (CREATED) is more descriptive. It tells the client that not only was the request successful, but a resource was created as a result. It’s always a good idea to use @ResponseStatus where appropriate to communicate the most descriptive and accurate HTTP status code to the client.
  //here the @RequestBody indicates that the body of the request should be converted to a Taco object and bound to the parameter.
  //Without it Spring MVC would assume that you want request parameters (either query parameters or form parameters) to be bound to the Taco object. 
  //But the @RequestBody annotation ensures that JSON in the request body is bound to the Taco object instead
  public Taco postTaco(@RequestBody Taco taco) {
    return tacoRepo.save(taco);
  }
  //end::postTaco[]
  
  //an endpoint that fetches a single taco by its ID.
  //controller’s base path is /design, this controller method handles GET   requests for /design/{id} , where the {id} portion of the path is a placeholder
  //notice this method will return NULL if no taco can be found by this id.
  //the next method returns a 404.
  @GetMapping("/{id}")
  public Taco tacoById(@PathVariable("id") Long id) {
    Optional<Taco> optTaco = tacoRepo.findById(id);
    if (optTaco.isPresent()) {
      return optTaco.get();
    }
    return null;
  }
  
//  @GetMapping("/{id}")
//  public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
//    Optional<Taco> optTaco = tacoRepo.findById(id);
//    if (optTaco.isPresent()) {
//      return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
//    }
//    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//  }

  
//tag::recents[]
}
//end::recents[]

