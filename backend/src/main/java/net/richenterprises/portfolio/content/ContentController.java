package net.richenterprises.portfolio.content;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 
* Public, read-only endpoints for the site's simple content: profile, links,
* education, certifications, experience and skills.
*/
@RestController
@RequestMapping("/api")
class ContentController {
    
    private final ContentRepository content;
        
    ContentController(ContentRepository content) {
        this.content = content;
    }
    
    @GetMapping("/profile")
    ProfileResponse profile() {
        return content.findProfile();
    }

    @GetMapping("/links")
    List<LinkResponse> links() {
        return content.findLinks();
    }
    
    @GetMapping("/education")
    List<EducationResponse> education() {
        return content.findEducation();
    }

    @GetMapping("/certifications")
    List<CertificationResponse> certifications() {
        return content.findCertifications();
    }
}
