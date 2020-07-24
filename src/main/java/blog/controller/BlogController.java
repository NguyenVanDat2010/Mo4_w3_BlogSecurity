package blog.controller;

import blog.model.Blog;
import blog.model.Category;
import blog.service.blog.IBlogService;
import blog.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("categories")
    public List<Category> categories(){
        return categoryService.findAll();
    }

    @GetMapping
    public String showList(@RequestParam("s") Optional<String> keyword, @PageableDefault(size = 3,direction = Sort.Direction.ASC,sort = "createdAt")Pageable pageable,Model model){
        Page<Blog> blogs;
        if (keyword.isPresent()){
            blogs = blogService.findAllByAuthorContainingOrCategory_NameContaining(keyword.get(),keyword.get(),pageable);
            model.addAttribute("keyword",keyword.get());
        }else {
            blogs = blogService.findAll(pageable);
        }
        model.addAttribute("blogs",blogs);
        return "blog/list";
    }

    @GetMapping("/create")
    public String showCreateForm( Model model){
        model.addAttribute("blog",new Blog());
        return "blog/create";
    }

    @PostMapping("/create")
    public String createBlog(@ModelAttribute("blog") Blog blog, Model model){
        blogService.save(blog);
        model.addAttribute("success","New customer created successfully!");
        model.addAttribute("blog",new Blog());
        return "blog/create";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.findById(id));
        return "/blog/edit";
    }

    @PostMapping("/edit")
    public String editBlog(@ModelAttribute("blog")Blog blog, RedirectAttributes redirectAttributes){
        blogService.save(blog);
        redirectAttributes.addFlashAttribute("success","Blog was updated successfully!");
        return "redirect:/blogs";
    }

    @GetMapping("/delete/{id}")
    public String deleteBlog(@PathVariable Long id,Model model){
        blogService.remove(id);
        model.addAttribute("success","Blog was deleted successfully!");
        return "redirect:/blogs";
    }

    @GetMapping("/view/{id}")
    public String viewBlog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.findById(id));
        return "blog/view";
    }

    @GetMapping("/error")
    public String error(){
        return "error-404";
    }
}
