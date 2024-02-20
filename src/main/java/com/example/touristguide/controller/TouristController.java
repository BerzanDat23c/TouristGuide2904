package com.example.touristguide.controller;

import com.example.touristguide.model.TouristAttraction;
import com.example.touristguide.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("attractions")
public class TouristController {


    private  final TouristService touristService;

    public TouristController(TouristService touristService) {

        this.touristService = touristService;
    }

    // CREATE endpoints

    @GetMapping("")
    public String getAllAttractions(Model model) {
        List<TouristAttraction> attractions = touristService.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "attractionList"; // Returnerer navnet på Thymeleaf-skabelonen
    }

    @PostMapping("/add")
    public String createAttraction(@ModelAttribute TouristAttraction attraction) {
        touristService.createAttraction(attraction);
        return "redirect:/attractions"; // Redirect til GET /attractions for at vise alle turistattraktioner
    }

    @GetMapping("/description/{description}")
    public String getAttractionByDescription(@PathVariable String description, Model model) {
        TouristAttraction attraction = touristService.getAttractionByDescription(description);
        if (attraction != null) {
            model.addAttribute("attraction", attraction);
            return "attractionDetails"; // Returnerer navnet på Thymeleaf-skabelonen
        } else {
            return "attractionNotFound"; // Returnerer navnet på Thymeleaf-skabelonen
        }
    }

    @GetMapping("/name/{name}")
    public String getAttractionsByName(@PathVariable String name, Model model) {
        List<TouristAttraction> attractions = touristService.getAttractionsByName(name);
        if (!attractions.isEmpty()) {
            model.addAttribute("attractions", attractions);
            return "attractionList"; // Returnerer navnet på Thymeleaf-skabelonen
        } else {
            return "attractionNotFound"; // Returnerer navnet på Thymeleaf-skabelonen
        }
    }

    // UPDATE endpoints
    @PutMapping("/update/{description}")
    public String updateAttractionByDescription(@PathVariable String description, @ModelAttribute TouristAttraction attraction) {
        touristService.updateAttractionByDescription(description, attraction);
        return "redirect:/attractions"; // Redirect til GET /attractions for at vise alle turistattraktioner
    }

    @PutMapping("/update")
    public String updateAttraction(@ModelAttribute TouristAttraction attraction) {
        touristService.updateAttraction(attraction);
        return "redirect:/attractions"; // Redirect til GET /attractions for at vise alle turistattraktioner
    }

    // DELETE endpoints
    @DeleteMapping("/delete/{description}")
    public String deleteAttractionByDescription(@PathVariable String description) {
        touristService.deleteAttractionByDescription(description);
        return "redirect:/attractions"; // Redirect til GET /attractions for at vise alle turistattraktioner
    }

    @GetMapping("/{name}")
    public String getAttractionDescriptionByName(@PathVariable String name, Model model) {
        TouristAttraction attraction = touristService.getAttractionByName(name);
        if (attraction != null) {
            model.addAttribute("description", attraction.getDescription());
            return "attractionDescription"; // Returnerer navnet på Thymeleaf-skabelonen
        } else {
            return "attractionNotFound"; // Returnerer navnet på Thymeleaf-skabelonen
        }
    }

    @GetMapping("/{name}/tags")
    public String getAttractionTagsByName(@PathVariable String name, Model model) {
        List<String> tags = touristService.getAttractionTagsByName(name);
        if (!tags.isEmpty()) {
            model.addAttribute("tags", tags);
            return "tagsList"; // Returnerer navnet på Thymeleaf-skabelonen
        } else {
            return "tagsNotFound"; // Returnerer navnet på Thymeleaf-skabelonen
        }
    }

    @GetMapping("/{name}/edit")
    public String getEditAttractionPage(@PathVariable String name, Model model) {
        String editPageContent = touristService.getEditAttractionPage(name);
        model.addAttribute("editContent", editPageContent);
        return "editPage"; // Returnerer navnet på Thymeleaf-skabelonen
    }


    /*@PostMapping("/add")
    public ResponseEntity<TouristAttraction> createAttraction(@RequestBody TouristAttraction attraction) {
        TouristAttraction createdAttraction = touristService.createAttraction(attraction);
        return new ResponseEntity<>(createdAttraction, HttpStatus.CREATED);
    }


    @GetMapping("/description/{description}")
    public ResponseEntity<TouristAttraction> getAttractionByDescription(@PathVariable String description) {
        TouristAttraction attraction = touristService.getAttractionByDescription(description);
        if (attraction != null) {
            return new ResponseEntity<>(attraction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<TouristAttraction>> getAttractionsByName(@PathVariable String name) {
        List<TouristAttraction> attractions = touristService.getAttractionsByName(name);
        if (!attractions.isEmpty()) {
            return new ResponseEntity<>(attractions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE endpoints
    @PutMapping("/update/{description}")
    public ResponseEntity<TouristAttraction> updateAttractionByDescription(@PathVariable String description, @RequestBody TouristAttraction attraction) {
        TouristAttraction updatedAttraction = touristService.updateAttractionByDescription(description, attraction);
        if (updatedAttraction != null) {
            return new ResponseEntity<>(updatedAttraction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<TouristAttraction> updateAttraction(@RequestBody TouristAttraction attraction) {
        TouristAttraction updatedAttraction = touristService.updateAttraction(attraction);
        if (updatedAttraction != null) {
            return new ResponseEntity<>(updatedAttraction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE endpoints
    @DeleteMapping("/delete/{description}")
    public ResponseEntity<String> deleteAttractionByDescription(@PathVariable String description) {
        boolean deleted = touristService.deleteAttractionByDescription(description);
        if (deleted) {
            return new ResponseEntity<>("Attraktion med beskrivelsen \"" + description + "\" er blevet slettet.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Attraktion med beskrivelsen \"" + description + "\" blev ikke fundet.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<String> getAttractionDescriptionByName(@PathVariable String name) {
        TouristAttraction attraction = touristService.getAttractionByName(name);
        if (attraction != null) {
            return new ResponseEntity<>(attraction.getDescription(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Attraktionen med navnet \"" + name + "\" blev ikke fundet.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{name}/tags")
    public ResponseEntity<List<String>> getAttractionTagsByName(@PathVariable String name) {
        List<String> tags = touristService.getAttractionTagsByName(name);
        if (tags != null) {
            return new ResponseEntity<>(tags, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{name}/edit")
    public ResponseEntity<String> getEditAttractionPage(@PathVariable String name) {
        // Tilføj logik for at hente og vise redigeringsformular
        return new ResponseEntity<>("Edit page for " + name, HttpStatus.OK);
    } */


}
