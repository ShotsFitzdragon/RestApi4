package restapi4.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@RestController
public class Controller {
    private static int id = 0;

    @RequestMapping(value = "/createGreeting", method = RequestMethod.POST)
    public Greeting createGreeting(@RequestBody String name) throws IOException {
        //New Greeting
        Greeting newGreeting = new Greeting(id++, name);

        //ObjectMapper provides functionality for reading and writing JSON
        ObjectMapper mapper = new ObjectMapper();

        //serialize greeting object to JSON
        mapper.writeValue(new File("./message.txt"), newGreeting);

        return newGreeting;
    }

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting greeting() throws IOException {
        //Object mapper provides functionality for reading JSON
        ObjectMapper mapper = new ObjectMapper();

        //Deserialize JSON to greeting object
        return mapper.readValue(new File("./message.txt"), Greeting.class);
    }

    @RequestMapping(value = "/updateGreeting", method = RequestMethod.PUT)
    public Greeting updateGreeting(@RequestBody String newMessage) throws IOException {
        //ObjectiveMapper provides functionality for reading and writing JSON
        ObjectMapper mapper = new ObjectMapper();

        String message = FileUtils.readFileToString(new File("./message.txt"), StandardCharsets.UTF_8);

        //deserialize JSON to greeting object
        Greeting greeting = mapper.readValue(message, Greeting.class);

        //update message
        greeting.setContent(newMessage);

        //Serialize greeting object to JSON
        mapper.writeValue(new File("./message.txt"), greeting);

        return greeting;
    }

    @RequestMapping(value = "/deleteGreeting", method = RequestMethod.DELETE)
    public void deleteGreeting(@RequestBody int id) throws IOException {
        //objectMapper provides functionality for reading and writing JSONO
        ObjectMapper mapper = new ObjectMapper();
        String message = FileUtils.readFileToString(new File("./message.txt"), StandardCharsets.UTF_8);
        //deserialize JSON to greeting object
        Greeting greeting = mapper.readValue(message, Greeting.class);
        if (greeting.getId() == id) {
            FileUtils.writeStringToFile(new File("./message.txt"), "", StandardCharsets.UTF_8.name());
        }
    }
}