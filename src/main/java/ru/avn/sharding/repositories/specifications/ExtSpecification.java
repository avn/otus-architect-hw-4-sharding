package ru.avn.sharding.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.Join;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

interface ExtSpecification<T> extends Specification<T> {

    default Specification<T> in(ListAttribute<T, ?> attr, @Nullable List<?> col) {

        if (col == null) {
            return null;
        }

        return (root, query, criteriaBuilder) -> {
            Join<T, ?> join = root.join(attr);

            if (col.size() == 1) {
                return criteriaBuilder.equal(join, col.get(0));
            }

            return join.in(col);
        };


    }

    default Specification<T> in(SingularAttribute<T, ?> attr, @Nullable List<?> col) {
        if (col == null) {
            return null;
        }

        if (col.size() == 1) {
            return (root, query, cb) -> cb.equal(root.get(attr), col);
        }

        return ((root, query, cb) -> root.get(attr)
                .in(col));

    }

}
