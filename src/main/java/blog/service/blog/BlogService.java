package blog.service.blog;

import blog.model.Blog;
import blog.model.Category;
import blog.repository.IBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class BlogService implements IBlogService{
    @Autowired
    private IBlogRepository blogRepository;


    @Override
    public List<Blog> findAll() {
        List<Blog> list = new ArrayList<>();
        blogRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> findAllByAuthorContainingOrCategory_NameContaining(@NotEmpty String author, @NotEmpty String category_name, Pageable pageable) {
        return blogRepository.findAllByAuthorContainingOrCategory_NameContaining(author,category_name,pageable);
    }

    @Override
    public Blog findById(Long id) {
        return blogRepository.findOne(id);
    }

    @Override
    public void save(Blog model) {
        blogRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        blogRepository.delete(id);
    }
}
