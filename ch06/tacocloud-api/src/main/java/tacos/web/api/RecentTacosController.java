package tacos.web.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import tacos.Taco;
import tacos.data.TacoRepository;

@RepositoryRestController
public class RecentTacosController {

  private TacoRepository tacoRepo;

  public RecentTacosController(TacoRepository tacoRepo) {
    this.tacoRepo = tacoRepo;
  }

  @GetMapping(path="/tacos/recent", produces="application/hal+json")
  //use Resources.wrap() to wrap the list of tacos as an instance of Resources<TacoResource>
  public ResponseEntity<Resources<TacoResource>> recentTacos() {
    PageRequest page = PageRequest.of(
                          0, 12, Sort.by("createdAt").descending());
    List<Taco> tacos = tacoRepo.findAll(page).getContent(); //fetch the tacos from the repository
    //pass the list of tacos to the toResources() method on a TacoResourceAssembler
    //this cycles through all of the Taco obj
    //now each taco contains a self link
    List<TacoResource> tacoResources = 
        new TacoResourceAssembler().toResources(tacos);
    Resources<TacoResource> recentResources = 
            new Resources<TacoResource>(tacoResources);
    
    
    //adding link to the entire list
    //have ControllerLinkBuilder derive the base URL from both the controller's base path 
    //and the method's mapped path
    //statically include the linkTo() and methodOn() methods
    //methodOn() takes the controller class and lets you make a call to the recentTacos()
    //which is intercepted by ControllerLinkBuilder and used to determine
    //the controller's base path and the path mapped to recentTacos()
    recentResources.add(
        linkTo(methodOn(RecentTacosController.class).recentTacos())
            .withRel("recents")); //this is the relation name
    return new ResponseEntity<>(recentResources, HttpStatus.OK);
  }

}
