package com.example.demo.controler;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class HelpController {

	@GetMapping("/help")
	public String showHelp() throws IOException {
		return new String( Files.readAllBytes( Paths.get("help.txt")));
	}
}
