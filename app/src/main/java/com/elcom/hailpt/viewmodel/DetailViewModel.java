package com.elcom.hailpt.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.elcom.hailpt.model.data.Article;
import com.elcom.hailpt.model.db.AppDatabase;
import com.elcom.hailpt.model.db.ArticleDao;

import javax.inject.Inject;

/**
 * @author ihsan on 12/28/17.
 */

public class DetailViewModel extends ViewModel {

    private final ArticleDao articleDao;

    @Inject
    DetailViewModel(AppDatabase database) {
        this.articleDao = database.articleDao();
    }

    public LiveData<Article> getArticle(int id) {
        return articleDao.get(id);
    }
}