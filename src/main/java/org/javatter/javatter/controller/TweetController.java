package  org.javatter.javatter.controller;

import java.util.List;

import org.javatter.javatter.entity.Tweet;
import org.javatter.javatter.repository.TweetRepository;
import org.javatter.javatter.form.TweetForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TweetController {
	
	@Autowired TweetRepository tweetRepository;
    
    @GetMapping("/tweets")
    public String getTweets(Model model) {
    	List<Tweet> tweets = tweetRepository.findAll();
    	model.addAttribute("tweets",tweets);
        return "tweets/index";
    }
    
    @GetMapping("/new_tweet")
    public String getNewTweet(Model model) {
    	TweetForm tweetForm = new TweetForm();
    	model.addAttribute("tweetForm", tweetForm);
    	return "tweets/new";
    }
    
    @PostMapping("/new_tweet")
    public String registerTweet(TweetForm tweetForm) {
    	Tweet tweet = new Tweet();
    	tweet.setTitle(tweetForm.getTitle());
    	tweet.setBody(tweetForm.getBody());
 
    	tweetRepository.save(tweet);

    	return "redirect:/tweets";
    }
}
