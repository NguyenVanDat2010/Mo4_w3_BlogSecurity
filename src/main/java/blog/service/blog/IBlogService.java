package blog.service.blog;

import blog.model.Blog;
import blog.model.Category;
import blog.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotEmpty;

public interface IBlogService extends IGeneralService<Blog> {
    Page<Blog> findAll(Pageable pageable);

    Page<Blog> findAllByAuthorContainingOrCategory_NameContaining(@NotEmpty String author, @NotEmpty String category_name, Pageable pageable);
}
