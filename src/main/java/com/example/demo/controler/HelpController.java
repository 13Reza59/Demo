package com.example.demo.controler;

import com.example.demo.model.Product;
import com.example.demo.payload.response.MessageResponse;
import org.springframework.security.access.prepost.PreAuthorize;
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
