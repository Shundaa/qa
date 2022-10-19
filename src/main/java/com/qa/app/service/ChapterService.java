package com.qa.app.service;

import com.qa.app.dao.request.NewChapterRequest;
import com.qa.app.dao.request.NewLessonRequest;
import com.qa.app.dao.request.NewLessonsToChapterRequest;
import com.qa.app.dao.response.ChapterDao;
import com.qa.app.entities.Lesson;

import java.util.List;

public interface ChapterService {
    List<ChapterDao> get();

    ChapterDao get(Long id);

    void delete(Long id);

    ChapterDao edit(Long id, NewChapterRequest newChapterRequest);

    ChapterDao newChapter(NewChapterRequest newChapterRequest);

    ChapterDao addNewLessonsToChapter(Long id, NewLessonsToChapterRequest newLessonsToChapterRequest);

    Lesson createNewLessonsToChapter(Long id, NewLessonRequest request);
}
