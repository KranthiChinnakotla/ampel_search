package drugsdb.recent.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
public class FileUploadController {

	public static final String uploadingdir = /*
												 * System.getProperty("user.dir")
												 "C:\\Users\\kchinnak\\Desktop" + "/uploadingdir/";*/
			"/home/ec2-user"+ "/uploadingdir/";
	public static  String filePath = null;
	private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {

		/*
		 * model.addAttribute("files", storageService .loadAll() .map(path ->
		 * MvcUriComponentsBuilder .fromMethodName(FileUploadController.class,
		 * "serveFile", path.getFileName().toString()) .build().toString())
		 * .collect(Collectors.toList()));
		 */
		//LOG.info(uploadingdir);
		File file = new File(uploadingdir);
		model.addAttribute("files", file.listFiles());

		return "uploadForm";
	}

	@PostMapping("/")
	public String uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles, RedirectAttributes redirectAttributes) throws IOException {
		for (MultipartFile uploadedFile : uploadingFiles) {
			filePath = uploadingdir + uploadedFile.getOriginalFilename();
			File file = new File(uploadingdir + uploadedFile.getOriginalFilename());
			uploadedFile.transferTo(file);
		}
		
		for(MultipartFile uploadFile: uploadingFiles){
			redirectAttributes.addFlashAttribute("message",
	                "You successfully uploaded " + uploadFile.getOriginalFilename() + "!");

			
		}
		
		return "redirect:/";
	}

}
