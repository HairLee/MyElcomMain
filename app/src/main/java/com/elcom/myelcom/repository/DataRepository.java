package com.elcom.myelcom.repository;

import android.arch.lifecycle.MutableLiveData;

import com.elcom.myelcom.core.AppSchedulerProvider;
import com.elcom.myelcom.core.BaseViewModel;
import com.elcom.myelcom.core.base.ArticleUtils;
import com.elcom.myelcom.model.api.Api;
import com.elcom.myelcom.model.data.Article;
import com.elcom.myelcom.model.data.Articles;
import com.elcom.myelcom.model.data.Source;
import com.elcom.myelcom.model.data.Sources;
import com.elcom.myelcom.model.db.AppDatabase;
import com.elcom.myelcom.model.db.ArticleDao;
import com.elcom.myelcom.model.db.SourceDao;
import com.elcom.myelcom.util.ConstantsApp;
import com.google.gson.JsonElement;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author ihsan on 12/28/17.
 */

public class DataRepository implements BaseViewModel {

    private final List<Source> sourceList;
    private CompositeDisposable disposables = new CompositeDisposable();
    private final Api api;
    private final MutableLiveData<List<Article>> articleMutableLiveData;
    private final MutableLiveData<JsonElement> notiMutableLiveData;
    private final MutableLiveData<List<Source>> sourceMutableLiveData;
    private final AppSchedulerProvider schedulerProvider;
    private final SourceDao sourceDao;
    private final ArticleDao articleDao;

    @Inject
    DataRepository(AppDatabase database, Api api, AppSchedulerProvider schedulerProvider) {
        this.api = api;
        this.schedulerProvider = schedulerProvider;
        sourceDao = database.sourceDao();
        articleDao = database.articleDao();
        sourceList = sourceDao.getAllList();
        articleMutableLiveData = new MutableLiveData<>();
        sourceMutableLiveData = new MutableLiveData<>();
        notiMutableLiveData = new MutableLiveData<>();
        sourceMutableLiveData.postValue(sourceList);
        articleMutableLiveData.postValue(articleDao.getAll());
    }

    public void insertSource(Source item) {
        sourceDao.insert(item);
    }

    public void deleteSource(String id) {
        sourceDao.delete(id);
    }

    public MutableLiveData<List<Source>> getSourceLiveList() {
        api.sources()
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map(sources -> {
                    if (sourceList != null) {
                        for (Source item : sources.getSources()) {
                            for (Source data : sourceList) {
                                if (item.getId().equals(data.getId())) {
                                    item.setSelected(true);
                                }
                            }
                        }
                    }
                    return sources;
                })
                .subscribe(new Observer<Sources>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(Sources sources) {
                        sourceMutableLiveData.postValue(sources.getSources());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return sourceMutableLiveData;
    }

    public MutableLiveData<List<Article>> getArticleLiveList() {
        api.topHeadlines(getQuery())
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map(ArticleUtils::formatDate)
                .subscribe(new Observer<Articles>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(Articles articles) {
                        articleDao.clear();
                        articleDao.insert(articles.getArticles());
                        articleMutableLiveData.postValue(articleDao.getAll());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return articleMutableLiveData;
    }



    public MutableLiveData<JsonElement> getNotificationCount() {
        api.getNotificationCount(ConstantsApp.BASE64_HEADER)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map(data -> data)
                .subscribe(new Observer<JsonElement>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(JsonElement sources) {
                        notiMutableLiveData.postValue(sources);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        return notiMutableLiveData;
    }

    private String getQuery() {
        StringBuilder builder = new StringBuilder();
        if (sourceList != null && sourceList.size() > 0) {
            for (Source item : sourceList) {
                builder.append(item.getId()).append(",");
            }
            builder.deleteCharAt(builder.lastIndexOf(","));
        }
        return builder.toString();
    }

    @Override
    public void onClear() {
        disposables.clear();
    }
}
