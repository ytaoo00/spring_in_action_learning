package tacos.web.api;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import tacos.Taco;

public class TacoResourceAssembler
       extends ResourceAssemblerSupport<Taco, TacoResource> {

	//a default constructor that informs the superclass ( Resource	AssemblerSupport ) 
	//that it will be using DesignTacoController to determine the base
	//path for any URLs in links it creates
	
  public TacoResourceAssembler() {
    super(DesignTacoController.class, TacoResource.class);
  }
  
  //instantiate a TacoResource given a Taco	
  @Override
  protected TacoResource instantiateResource(Taco taco) {
    return new TacoResource(taco);
  }

  //this method is strictly mandatory when extending ResourceAssemblerSupport
  //create a TacoResource object from a Taco and to
  //automatically give it a self link with the url
  //being derived from the Taco object's id property
  @Override
  public TacoResource toResource(Taco taco) {
    return createResourceWithId(taco.getId(), taco);
  }

  	//Whereas instantiateResource() is intended to only instantiate a Resource object, 
  //toResource() is intended not only to create the Resource object, 
  //but also to populate it with links. Under the covers,
  //toResource() will call instantiateResource() .
}
