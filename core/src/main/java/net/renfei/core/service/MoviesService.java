package net.renfei.core.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.MovieClassificationDTO;
import net.renfei.core.entity.MoviesListDTO;
import net.renfei.dao.entity.*;
import net.renfei.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MoviesService extends BaseService {

    /**
     * 获取全部电影列表
     *
     * @param pages
     * @param rows
     * @return
     */
    public MoviesListDTO getAllMovies(String pages, String rows) {
        int intPage = convertPage(pages), intRows = convertRows(rows);
        MovieDOExample movieDOExample = new MovieDOExample();
        movieDOExample.setOrderByClause("years DESC,update_time DESC");
        movieDOExample.createCriteria();
        Page page = PageHelper.startPage(intPage, intRows);
        return convert(movieDOMapper.selectByExampleWithBLOBs(movieDOExample), page.getTotal());
    }

    public MoviesListDTO getMoviesListByCat(String catname, String pages, String rows) {
        if (stringUtil.isEmpty(catname)) {
            return null;
        }
        CategoryDOExample categoryDOExample = new CategoryDOExample();
        categoryDOExample.createCriteria().andEnNameEqualTo(catname.trim());
        List<CategoryDO> categoryDOS = categoryDOMapper.selectByExampleWithBLOBs(categoryDOExample);
        if (categoryDOS != null && categoryDOS.size() > 0) {
            int intPage = convertPage(pages), intRows = convertRows(rows);
            MovieDOExample movieDOExample = new MovieDOExample();
            movieDOExample.setOrderByClause("years DESC,update_time DESC");
            movieDOExample.createCriteria()
                    .andCategoryIdEqualTo(categoryDOS.get(0).getId());
            Page page = PageHelper.startPage(intPage, intRows);
            return convert(movieDOMapper.selectByExampleWithBLOBs(movieDOExample), page.getTotal());
        } else {
            return null;
        }

    }

    /**
     * 根据ID获取电影详情
     *
     * @param id
     * @param recordViews
     * @return
     */
    public MovieDOWithBLOBs getMovieById(String id, boolean recordViews) {
        Long ID = 0L;
        if (!stringUtil.isEmpty(id)) {
            try {
                ID = Long.valueOf(id);
                MovieDOWithBLOBs movieDOWithBLOBs = movieDOMapper.selectByPrimaryKey(ID);
                if (movieDOWithBLOBs != null && recordViews) {
                    updateView(movieDOWithBLOBs);
                }
                return movieDOWithBLOBs;
            } catch (NumberFormatException nfe) {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<MovieClassificationDTO> getAllMovieClassification() {
        TypeDOExample typeDOExample = new TypeDOExample();
        typeDOExample.createCriteria().andTypeNameEqualTo("Movie");
        List<TypeDO> typeDOS = typeDOMapper.selectByExample(typeDOExample);
        if (typeDOS != null && typeDOS.size() > 0) {
            CategoryDOExample categoryDOExample = new CategoryDOExample();
            categoryDOExample.createCriteria().andTypeIdEqualTo(typeDOS.get(0).getId());
            List<CategoryDO> categoryDOS = categoryDOMapper.selectByExampleWithBLOBs(categoryDOExample);
            if (categoryDOS != null && categoryDOS.size() > 0) {
                List<MovieClassificationDTO> movieClassificationDTOS = new ArrayList<>();
                for (CategoryDO cat : categoryDOS
                ) {
                    MovieClassificationDTO movieClassificationDTO = ejbGenerator.convert(cat, MovieClassificationDTO.class);
                    MovieDOExample movieDOExample = new MovieDOExample();
                    movieDOExample.createCriteria().andCategoryIdEqualTo(cat.getId());
                    Page page = PageHelper.startPage(1, 1);
                    movieDOMapper.selectByExample(movieDOExample);
                    movieClassificationDTO.setCountMovie(page.getTotal());
                    movieClassificationDTOS.add(movieClassificationDTO);
                }
                return movieClassificationDTOS;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    private MoviesListDTO convert(List<MovieDOWithBLOBs> movieDOWithBLOBs, Long count) {
        MoviesListDTO moviesListDTO = new MoviesListDTO();
        moviesListDTO.setCount(count);
        moviesListDTO.setMovieDOList(movieDOWithBLOBs);
        return moviesListDTO;
    }
}