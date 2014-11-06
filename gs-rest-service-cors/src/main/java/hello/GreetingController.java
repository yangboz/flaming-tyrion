package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("x")
@ApiVersion(1)
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public @ResponseBody Greeting greeting(
            @RequestParam(value="name", required=false, defaultValue="World") String name) {
    	System.out.println("==== in greeting ====");
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
    @RequestMapping("a")
    public @ResponseBody String a() { return "a";}         // maps to /v1/x/a

    @RequestMapping("b")
    @ApiVersion(2)
    public @ResponseBody String b() { return "b";}        // maps to /v2/x/b

    @RequestMapping("c")
    @ApiVersion({1,3})
    public @ResponseBody String c() { return "c";}        // maps to /v1/x/c
                        //  and to /v3/x/c
}