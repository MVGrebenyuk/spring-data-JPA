package com.skillbox.spring.web.repositories.specifications;

import com.skillbox.spring.web.entities.Student;
import org.springframework.data.jpa.domain.Specification;

/**
 * Этот класс представляет собой набор методов, которые по необходимости вызываются для формирования одной спецификации,
 * либо предиката
 */
public class StudentsSpecifications {

    /**
     * "(root, criteriaQuery, criteriaBuilder) -> criteriaBuilder... " стандартная запись. В качестве root здесь выступает
     * объект, заключенный в ковычки в обхявлении спецификации. В нашем случае - Student: Specification<Student>
     * criteriaQuery, criteriaBuilder - стандартные объекты, которые используются при построении спецификации.
     * методы criteriaBuilder (например, criteriaBuilder.greaterThanOrEqualTo) принмиают в кач-ве аргумента поле сущности
     * через root.get(наименование поля), и значение.
     *
     * Изначально, спецификация при создании имеет вид SELECT * FROM STUDENTS WHERE null;
     * Если применяем спецификацию ниже, будет подготовлен запрос типа: SELECT * FROM STUDENTS WHERE score >= :score
     */
    public static Specification<Student> scoreGreaterOrEqualsThan(Integer score1) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("score"), score1);
    }

    /**
     * При применении этого метода совместно с первым, спецификация приобретет вид:
     * SELECT * FROM STUDENTS WHERE score >= :score1 AND score <= :score2
     * Т.е. спецификация будет обогощаться дополнительными параметрами и условиями
     * Если только эта спецификация, то:
     * SELECT * FROM STUDENTS WHERE score <= :score2
     */
    public static Specification<Student> scoreLessThanOrEqualsThan(Integer score2) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("score"), score2);
    }

    /**
     *
     * SELECT * FROM STUDENTS WHERE name like 'namePart
     *
     */
    public static Specification<Student> nameLike(String namePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), String.format("%%%s%%", namePart));
    }
}
