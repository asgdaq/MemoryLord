package com.example.WorldApp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;


@SpringBootApplication
@Controller
public class WorldAppApplication {

	String ask;
	static Map<String , String> map = new LinkedHashMap<>();
	 int count = 0;
	String type ="";
	int temp = 0;

	int corect;

	public static void main(String[] args) {

		SpringApplicationBuilder builder = new SpringApplicationBuilder(WorldAppApplication.class);

		builder.headless(false);

		ConfigurableApplicationContext context = builder.run(args);

		//SpringApplication.run(WorldAppApplication.class, args);
	}

	@GetMapping("/")
	public String start(){

		return "WorldGame.html";
	}

	@PostMapping("clear")
	public String clear(){
		map.clear();
		return "WorldGame.html";
	}

	@PostMapping("/element")
	public String element(@RequestParam ("key") String key , @RequestParam("elem") String elem  , Model model){

		System.out.println(key + " - > " + elem);

		map.put(key , elem);
		map.remove("","");
		System.out.println(map);



		model.addAttribute("map",map);

		return "WorldGame.html";
	}

	@GetMapping("/TypeGame.html")
	public String typeGame(){
		return "TypeGame.html";
	}




	@PostMapping("/select")
	public String typeGame(@RequestParam ("type") String tip ,@RequestParam("nr") int nr , Model model){

        try{
            System.out.println(tip + "->" + nr);
            count = nr*map.size();
            temp = nr*map.size();
            type = tip;

            System.out.println(type +" -> tipul ");
            GameInterface(model);
            map.remove("" , "");
            corect = count;
        }
		catch (Exception e){
            return "Sptii.html";
        }

		return "GameInterface.html";
	}

	HashSet<String > set = new HashSet<>();
	Set<String> doi = new HashSet<>();
	int a = 0;
	public String question(){

		int nr = count;

		RandomQuestion randomQuestion = new RandomQuestion();
		String text = switch (type) {
			case "key" -> randomQuestion.key((HashMap<String, String>) map , set);
			case "value" -> randomQuestion.value(map, doi);
			case"random" -> randomQuestion.random(map  , set , doi , a);
			default -> null;
		};
		ask = text;
		if(a == 0){
			a = 1;
		}
		else{
			a = 0;
		}

		System.out.println(text);
		return text;
	}

	public void GameInterface(Model model){


		String resultat = question();

		System.out.println(resultat);

		if(!resultat.equals("")){
			model.addAttribute("resultat",resultat);

		}

	}


	String ans;
	boolean statu ;
	int b = 0;

	@PostMapping("/game")
	public String game(@RequestParam("question") String question , Model model){
		count--;

        RandomQuestion randomQuestion = new RandomQuestion();
        ans = question;
        boolean status = randomQuestion.checker(question ,type , a ,map , ask);
        statu = status;

        if(!status){
            corect--;
        }


		System.out.println(status);

		model.addAttribute("b" , b);
		model.addAttribute("status" , status);

        if(count == 0){

            return fifnishGameInterface(model);
        }

		b++;

		System.out.println(b);

		GameInterface(model);


		return "GameInterface.html";
	}



	@GetMapping("/FinishGame.html")
	public String fifnishGameInterface(Model model){

		System.out.println(corect);
		model.addAttribute("corect" , corect);
		model.addAttribute("din" , temp);
		corect = 0;

		set.clear();
		return "FinishGame.html";
	}




	@PostMapping("/printtest")
	public String print(Model model  , Model model1){
		System.out.println("esteeeeeeeeee!!!!!!!!");
		PrintTest printTest = new PrintTest() ;
		String text = printTest.getData(type , temp , (HashMap<String, String>) map , set , (HashSet<String>) doi);
		model1.addAttribute("text" , text);
		//printTest.printFile(text);
		return "Print.html";
	}

	@GetMapping("WorldGame.html")
	public String restat(Model mdel){

		set.clear();
		doi.clear();
		return element("","" , mdel);
	}


	@GetMapping("/Guide.html")
	public String guide(Model model){
		model.addAttribute("image1", "image1.jpg");

		return "Guide.html";
	}


	@PostMapping("/like")

	public String like(Model model) throws IOException, ExecutionException, InterruptedException {

		Firebase firebase = new Firebase();
		firebase.baad("positive");
		boolean like = true;
		model.addAttribute("like" , like);

		return "Guide.html";

	}


	@GetMapping("/request.html")
	public String req(){
		return "request.html";
	}

	@PostMapping("/send")
	public String request(@RequestParam("email")String email , @RequestParam("topic")  String topic , @RequestParam("comment") String desc)
			throws IOException, ExecutionException, InterruptedException {


		Firebase firebase = new Firebase();
		firebase.baad("negative");
		firebase.addData(email , topic , desc);



		return "Guide.html";
	}
}
