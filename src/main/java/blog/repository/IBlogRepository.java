package blog.repository;

import blog.model.Blog;
import blog.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;

@Transactional
public interface IBlogRepository extends PagingAndSortingRepository<Blog,Long> {
    Page<Blog> findAllByAuthorContainingOrCategory_NameContaining(@NotEmpty String author, @NotEmpty String category_name, Pageable pageable);
}
