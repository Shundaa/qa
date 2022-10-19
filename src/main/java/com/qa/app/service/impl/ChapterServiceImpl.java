package com.qa.app.service.impl;

import com.qa.app.dao.request.NewChapterRequest;
import com.qa.app.dao.request.NewLessonRequest;
import com.qa.app.dao.request.NewLessonsToChapterRequest;
import com.qa.app.dao.response.ChapterDao;
import com.qa.app.entities.Chapter;
import com.qa.app.entities.Lesson;
import com.qa.app.exception.ResourceNotFoundException;
import com.qa.app.repository.ChapterRepository;
import com.qa.app.repository.LessonRepository;
import com.qa.app.service.ChapterService;
import com.qa.app.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final LessonRepository lessonRepository;

    @Override
    public List<ChapterDao> get() {
        return ObjectMapperUtils.mapAll(chapterRepository.findAll(), ChapterDao.class);
    }

    @Override
    public ChapterDao get(Long id) {
        return ObjectMapperUtils.map(findById(id),
                ChapterDao.class);
    }

    @Override
    public void delete(Long id) {
        chapterRepository.deleteById(id);
    }

    @Override
    public ChapterDao edit(Long id, NewChapterRequest newChapterRequest) {
        Chapter chapter = findById(id);
        if (Objects.nonNull(newChapterRequest.getName()) && !newChapterRequest.getName().isEmpty()) {
            chapter.setName(newChapterRequest.getName());
        }
        return ObjectMapperUtils.map(chapterRepository.save(chapter), ChapterDao.class);
    }

    @Override
    public ChapterDao newChapter(NewChapterRequest newChapterRequest) {
        Chapter chapter = Chapter.builder()
                .name(newChapterRequest.getName())
                .build();
        return ObjectMapperUtils.map(chapterRepository.save(chapter), ChapterDao.class);
    }

    @Override
    public ChapterDao addNewLessonsToChapter(Long id, NewLessonsToChapterRequest newLessonsToChapterRequest) {
        Chapter chapter = findById(id);
        List<Lesson> lessons = chapter.getLessons();
        newLessonsToChapterRequest.getLessonsId().forEach(lessonId -> {
            Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                    () -> new ResourceNotFoundException("Chapter with id: " + id + " Not Found"));
            lesson.setChapter(chapter);
            lessons.add(lesson);
        });
        return ObjectMapperUtils.map(chapterRepository.save(chapter), ChapterDao.class);
    }

    @Override
    public Lesson createNewLessonsToChapter(Long id, NewLessonRequest request) {
        Chapter chapter = findById(id);
        List<Lesson> lessons = chapter.getLessons();
        Lesson lesson = Lesson.builder()
                .text(request.getText())
                .name(request.getName())
                .chapter(chapter)
                .build();
        if (Objects.isNull(lessons) || lessons.isEmpty()) {
            lessons = new ArrayList<>();
        }
        Lesson lessonSaved = lessonRepository.save(lesson);
        lessons.add(lessonSaved);
        chapter.setLessons(lessons);
        return ObjectMapperUtils.map(lessonSaved, Lesson.class);
    }

    private Chapter findById(Long id) {
        return chapterRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Chapter with id: " + id + " Not Found"));
    }
}
