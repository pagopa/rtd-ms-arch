/*
 * 
 */
package eu.sia.meda.connector.jpa;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.connector.jpa.utils.DefaultQueryHintsExt;
import eu.sia.meda.connector.jpa.utils.QueryHintsExt;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * The Class JPAConnectorImpl.
 *
 * @param <T> the generic type
 * @param <ID> the generic type
 */
@NoRepositoryBean
public class JPAConnectorImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements JPAConnector<T, ID> {
   
   /** The logger. */
   private final Logger logger;
   
   /** The Constant ID_MUST_NOT_BE_NULL. */
   private static final String ID_MUST_NOT_BE_NULL = "The given id must not be null!";
   
   /** The Constant METHOD_INTERCEPTED. */
   private static final String METHOD_INTERCEPTED = "Method {} intercepted by the Oracle-JPA connector.";
   
   /** The entity information. */
   private final JpaEntityInformation<T, ?> entityInformation;
   
   /** The em. */
   private final EntityManager em;
   
   /** The provider. */
   private final PersistenceProvider provider;
   
   /** The metadata. */
   @Nullable
   private CrudMethodMetadata metadata;

   /**
    * Instantiates a new JPA connector impl.
    *
    * @param entityInformation the entity information
    * @param entityManager the entity manager
    */
   public JPAConnectorImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
      super(entityInformation, entityManager);
      this.logger = LoggerUtils.getLogger(this.getClass());
      Assert.notNull(entityInformation, "JpaEntityInformation must not be null!");
      Assert.notNull(entityManager, "EntityManager must not be null!");
      this.entityInformation = entityInformation;
      this.em = entityManager;
      this.provider = PersistenceProvider.fromEntityManager(entityManager);
   }

   /**
    * Instantiates a new JPA connector impl.
    *
    * @param domainClass the domain class
    * @param em the em
    */
   public JPAConnectorImpl(Class<T> domainClass, EntityManager em) {
      this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
   }

   /**
    * Delete by id.
    *
    * @param id the id
    */
   @Transactional
   public void deleteById(ID id) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      Assert.notNull(id, ID_MUST_NOT_BE_NULL);
      this.delete(this.findById(id).orElseThrow(() -> {
         return new EmptyResultDataAccessException(String.format("No %s entity with id %s exists!", this.entityInformation.getJavaType(), id), 1);
      }));
   }

   /**
    * Delete.
    *
    * @param entity the entity
    */
   @Transactional
   public void delete(T entity) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      Assert.notNull(entity, "The entity must not be null!");
      this.em.remove(this.em.contains(entity) ? entity : this.em.merge(entity));
   }

   /**
    * Delete all.
    *
    * @param entities the entities
    */
   @Transactional
   public void deleteAll(Iterable<? extends T> entities) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      Assert.notNull(entities, "The given Iterable of entities must not be null!");
      Iterator var1 = this.findAll().iterator();

      while(var1.hasNext()) {
         T element = (T) var1.next();
         this.delete(element);
      }

   }

   /**
    * Delete in batch.
    *
    * @param entities the entities
    */
   @Transactional
   public void deleteInBatch(Iterable<T> entities) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      Assert.notNull(entities, "The given Iterable of entities not be null!");
      if (entities.iterator().hasNext()) {
         QueryUtils.applyAndBind(QueryUtils.getQueryString("delete from %s x", this.entityInformation.getEntityName()), entities, this.em).executeUpdate();
      }

   }

   /**
    * Delete all.
    */
   @Transactional
   public void deleteAll() {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      Iterator var1 = this.findAll().iterator();

      while(var1.hasNext()) {
         T element = (T) var1.next();
         this.delete(element);
      }

   }

   /**
    * Delete all in batch.
    */
   @Transactional
   public void deleteAllInBatch() {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      this.em.createQuery(this.getDeleteAllQueryStringChild()).executeUpdate();
   }

   /**
    * Find by id.
    *
    * @param id the id
    * @return the optional
    */
   public Optional<T> findById(ID id) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      Assert.notNull(id, ID_MUST_NOT_BE_NULL);
      Class<T> domainType = this.getDomainClass();
      if (this.metadata == null) {
         return Optional.ofNullable(this.em.find(domainType, id));
      } else {
         LockModeType type = this.metadata.getLockModeType();
         Map<String, Object> hints = this.getQueryHintsExt().withFetchGraphs(this.em).asMap();
         return Optional.ofNullable(type == null ? this.em.find(domainType, id, hints) : this.em.find(domainType, id, type, hints));
      }
   }

   /**
    * Gets the one.
    *
    * @param id the id
    * @return the one
    */
   public T getOne(ID id) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      Assert.notNull(id, ID_MUST_NOT_BE_NULL);
      return this.em.getReference(this.getDomainClass(), id);
   }

   /**
    * Exists by id.
    *
    * @param id the id
    * @return true, if successful
    */
   public boolean existsById(ID id) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      Assert.notNull(id, ID_MUST_NOT_BE_NULL);
      if (this.entityInformation.getIdAttribute() == null) {
         return this.findById(id).isPresent();
      } else {
         String placeholder = this.provider.getCountQueryPlaceholder();
         String entityName = this.entityInformation.getEntityName();
         Iterable<String> idAttributeNames = this.entityInformation.getIdAttributeNames();
         String existsQuery = QueryUtils.getExistsQueryString(entityName, placeholder, idAttributeNames);
         TypedQuery<Long> query = this.em.createQuery(existsQuery, Long.class);
         if (!this.entityInformation.hasCompositeId()) {
            query.setParameter((String)idAttributeNames.iterator().next(), id);
            return (Long)query.getSingleResult() == 1L;
         } else {
            Iterator var7 = idAttributeNames.iterator();

            while(var7.hasNext()) {
               String idAttributeName = (String)var7.next();
               Object idAttributeValue = this.entityInformation.getCompositeIdAttributeValue(id, idAttributeName);
               boolean complexIdParameterValueDiscovered = idAttributeValue != null && !query.getParameter(idAttributeName).getParameterType().isAssignableFrom(idAttributeValue.getClass());
               if (complexIdParameterValueDiscovered) {
                  return this.findById(id).isPresent();
               }

               query.setParameter(idAttributeName, idAttributeValue);
            }

            return (Long)query.getSingleResult() == 1L;
         }
      }
   }

   /**
    * Find all.
    *
    * @return the list
    */
   public List<T> findAll() {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      return this.getQuery((Specification)null, Sort.unsorted()).getResultList();
   }

   /**
    * Find all by id.
    *
    * @param ids the ids
    * @return the list
    */
   public List<T> findAllById(Iterable<ID> ids) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      Assert.notNull(ids, "The given Iterable of Id's must not be null!");
      if (!ids.iterator().hasNext()) {
         return Collections.emptyList();
      } else if (!this.entityInformation.hasCompositeId()) {
         JPAConnectorImpl.ByIdsSpecification specification = new JPAConnectorImpl.ByIdsSpecification(this.entityInformation);
         TypedQuery<T> query = this.getQuery(specification, Sort.unsorted());
         return query.setParameter(specification.parameter, ids).getResultList();
      } else {
         List<T> results = new ArrayList<>();
         Iterator<ID> var3 = ids.iterator();

         while(var3.hasNext()) {
            ID id = var3.next();
            this.findById(id).ifPresent(results::add);
         }

         return results;
      }
   }

   /**
    * Find all.
    *
    * @param sort the sort
    * @return the list
    */
   public List<T> findAll(Sort sort) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      return this.getQuery((Specification)null, sort).getResultList();
   }

   /**
    * Find all.
    *
    * @param pageable the pageable
    * @return the page
    */
   public Page<T> findAll(Pageable pageable) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      return (Page)(isUnpaged(pageable) ? new PageImpl(this.findAll()) : this.findAll((Specification)null, pageable));
   }

   /**
    * Find one.
    *
    * @param spec the spec
    * @return the optional
    */
   public Optional<T> findOne(@Nullable Specification<T> spec) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());

      try {
         return Optional.of(this.getQuery(spec, Sort.unsorted()).getSingleResult());
      } catch (NoResultException var3) {
         return Optional.empty();
      }
   }

   /**
    * Find all.
    *
    * @param spec the spec
    * @return the list
    */
   public List<T> findAll(@Nullable Specification<T> spec) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      return this.getQuery(spec, Sort.unsorted()).getResultList();
   }

   /**
    * Find all.
    *
    * @param spec the spec
    * @param pageable the pageable
    * @return the page
    */
   public Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      TypedQuery<T> query = this.getQuery(spec, pageable);
      return (Page)(isUnpaged(pageable) ? new PageImpl(query.getResultList()) : this.readPage(query, this.getDomainClass(), pageable, spec));
   }

   /**
    * Find all.
    *
    * @param spec the spec
    * @param sort the sort
    * @return the list
    */
   public List<T> findAll(@Nullable Specification<T> spec, Sort sort) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      return this.getQuery(spec, sort).getResultList();
   }

   /**
    * Count.
    *
    * @param <S> the generic type
    * @param example the example
    * @return the long
    */
   public <S extends T> long count(Example<S> example) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      return executeCountQuery(this.getCountQuery(new JPAConnectorImpl.ExampleSpecification(example), example.getProbeType()));
   }

   /**
    * Exists.
    *
    * @param <S> the generic type
    * @param example the example
    * @return true, if successful
    */
   public <S extends T> boolean exists(Example<S> example) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      return !this.getQueryExt(new JPAConnectorImpl.ExampleSpecification(example), example.getProbeType(), Sort.unsorted()).getResultList().isEmpty();
   }

   /**
    * Find all.
    *
    * @param <S> the generic type
    * @param example the example
    * @return the list
    */
   public <S extends T> List<S> findAll(Example<S> example) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      return this.getQueryExt(new JPAConnectorImpl.ExampleSpecification(example), example.getProbeType(), Sort.unsorted()).getResultList();
   }

   /**
    * Find all.
    *
    * @param <S> the generic type
    * @param example the example
    * @param sort the sort
    * @return the list
    */
   public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      return this.getQueryExt(new JPAConnectorImpl.ExampleSpecification(example), example.getProbeType(), sort).getResultList();
   }

   /**
    * Find all.
    *
    * @param <S> the generic type
    * @param example the example
    * @param pageable the pageable
    * @return the page
    */
   public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      JPAConnectorImpl.ExampleSpecification<S> spec = new JPAConnectorImpl.ExampleSpecification(example);
      Class<S> probeType = example.getProbeType();
      TypedQuery<S> query = this.getQuery(new JPAConnectorImpl.ExampleSpecification(example), probeType, pageable);
      return (Page)(isUnpaged(pageable) ? new PageImpl(query.getResultList()) : this.readPage(query, probeType, pageable, spec));
   }

   /**
    * Count.
    *
    * @return the long
    */
   public long count() {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      return (Long)this.em.createQuery(this.getCountQueryStringChild(), Long.class).getSingleResult();
   }

   /**
    * Count.
    *
    * @param spec the spec
    * @return the long
    */
   public long count(@Nullable Specification<T> spec) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      return executeCountQuery(this.getCountQuery(spec, this.getDomainClass()));
   }

   /**
    * Save.
    *
    * @param <S> the generic type
    * @param entity the entity
    * @return the s
    */
   @Transactional
   public <S extends T> S save(S entity) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      if (this.entityInformation.isNew(entity)) {
         this.em.persist(entity);
         return entity;
      } else {
         return this.em.merge(entity);
      }
   }

   /**
    * Save and flush.
    *
    * @param <S> the generic type
    * @param entity the entity
    * @return the s
    */
   @Transactional
   public <S extends T> S saveAndFlush(S entity) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      S result = this.save(entity);
      this.flush();
      return result;
   }

   /**
    * Save all.
    *
    * @param <S> the generic type
    * @param entities the entities
    * @return the list
    */
   @Transactional
   public <S extends T> List<S> saveAll(Iterable<S> entities) {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      Assert.notNull(entities, "The given Iterable of entities not be null!");
      List<S> result = new ArrayList();
      Iterator var3 = entities.iterator();

      while(var3.hasNext()) {
         S entity = (S) var3.next();
         result.add(this.save(entity));
      }

      return result;
   }

   /**
    * Flush.
    */
   @Transactional
   public void flush() {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      this.em.flush();
   }

   /**
    * Execute SQL query.
    *
    * @param query the query
    * @return the list
    * @throws Exception the exception
    */
   public List<T> executeSQLQuery(String query) throws Exception {
      this.logger.debug(LoggerUtils.formatArchRow(METHOD_INTERCEPTED), Thread.currentThread().getStackTrace()[1].getMethodName());
      Query q = this.em.createNativeQuery(query);
      return q.getResultList();
   }

   /**
    * Gets the delete all query string child.
    *
    * @return the delete all query string child
    */
   private String getDeleteAllQueryStringChild() {
      return QueryUtils.getQueryString("delete from %s x", this.entityInformation.getEntityName());
   }

   /**
    * Gets the count query.
    *
    * @param <S> the generic type
    * @param spec the spec
    * @param domainClass the domain class
    * @return the count query
    */
   protected <S extends T> TypedQuery<Long> getCountQuery(@Nullable Specification<S> spec, Class<S> domainClass) {
      CriteriaBuilder builder = this.em.getCriteriaBuilder();
      CriteriaQuery<Long> query = builder.createQuery(Long.class);
      Root<S> root = this.applySpecificationToCriteriaChild(spec, domainClass, query);
      if (query.isDistinct()) {
         query.select(builder.countDistinct(root));
      } else {
         query.select(builder.count(root));
      }

      query.orderBy(Collections.emptyList());
      return this.em.createQuery(query);
   }

   /**
    * Apply specification to criteria child.
    *
    * @param <S> the generic type
    * @param <U> the generic type
    * @param spec the spec
    * @param domainClass the domain class
    * @param query the query
    * @return the root
    */
   private <S, U extends T> Root<U> applySpecificationToCriteriaChild(@Nullable Specification<U> spec, Class<U> domainClass, CriteriaQuery<S> query) {
      Assert.notNull(domainClass, "Domain class must not be null!");
      Assert.notNull(query, "CriteriaQuery must not be null!");
      Root<U> root = query.from(domainClass);
      if (spec == null) {
         return root;
      } else {
         CriteriaBuilder builder = this.em.getCriteriaBuilder();
         Predicate predicate = spec.toPredicate(root, query, builder);
         if (predicate != null) {
            query.where(predicate);
         }

         return root;
      }
   }

   /**
    * Apply repository method metadata child.
    *
    * @param <S> the generic type
    * @param query the query
    * @return the typed query
    */
   private <S> TypedQuery<S> applyRepositoryMethodMetadataChild(TypedQuery<S> query) {
      if (this.metadata == null) {
         return query;
      } else {
         LockModeType type = this.metadata.getLockModeType();
         TypedQuery<S> toReturn = type == null ? query : query.setLockMode(type);
         this.applyQueryHintsChild(toReturn);
         return toReturn;
      }
   }

   /**
    * Gets the query hints ext.
    *
    * @return the query hints ext
    */
   protected QueryHintsExt getQueryHintsExt() {
      return (QueryHintsExt)(this.metadata == null ? QueryHintsExt.NoHints.INSTANCE : DefaultQueryHintsExt.of(this.entityInformation, this.metadata));
   }

   /**
    * Gets the query ext.
    *
    * @param <S> the generic type
    * @param spec the spec
    * @param domainClass the domain class
    * @param sort the sort
    * @return the query ext
    */
   protected <S extends T> TypedQuery<S> getQueryExt(@Nullable Specification<S> spec, Class<S> domainClass, Sort sort) {
      CriteriaBuilder builder = this.em.getCriteriaBuilder();
      CriteriaQuery<S> query = builder.createQuery(domainClass);
      Root<S> root = this.applySpecificationToCriteriaChild(spec, domainClass, query);
      query.select(root);
      if (sort.isSorted()) {
         query.orderBy(QueryUtils.toOrders(sort, root, builder));
      }

      return this.applyRepositoryMethodMetadataChild(this.em.createQuery(query));
   }

   /**
    * Apply query hints child.
    *
    * @param query the query
    */
   private void applyQueryHintsChild(Query query) {
      Iterator var2 = this.getQueryHintsExt().withFetchGraphs(this.em).iterator();

      while(var2.hasNext()) {
         Entry<String, Object> hint = (Entry)var2.next();
         query.setHint((String)hint.getKey(), hint.getValue());
      }

   }

   /**
    * Execute count query.
    *
    * @param query the query
    * @return the long
    */
   private static Long executeCountQuery(TypedQuery<Long> query) {
      Assert.notNull(query, "TypedQuery must not be null!");
      List<Long> totals = query.getResultList();
      Long total = 0L;

      Long element;
      for(Iterator var3 = totals.iterator(); var3.hasNext(); total = total + (element == null ? 0L : element)) {
         element = (Long)var3.next();
      }

      return total;
   }

   /**
    * Checks if is unpaged.
    *
    * @param pageable the pageable
    * @return true, if is unpaged
    */
   private static boolean isUnpaged(Pageable pageable) {
      return pageable.isUnpaged();
   }

   /**
    * Gets the count query string child.
    *
    * @return the count query string child
    */
   private String getCountQueryStringChild() {
      String countQuery = String.format("select count(%s) from %s x", this.provider.getCountQueryPlaceholder(), "%s");
      return QueryUtils.getQueryString(countQuery, this.entityInformation.getEntityName());
   }

   /**
    * The Class ByIdsSpecification.
    *
    * @param <T> the generic type
    */
   private static final class ByIdsSpecification<T> implements Specification<T> {
      
      /** The entity information. */
      private final JpaEntityInformation<T, ?> entityInformation;
      
      /** The parameter. */
      @Nullable
      ParameterExpression<Iterable> parameter;

      /**
       * Instantiates a new by ids specification.
       *
       * @param entityInformation the entity information
       */
      ByIdsSpecification(JpaEntityInformation<T, ?> entityInformation) {
         this.entityInformation = entityInformation;
      }

      /**
       * To predicate.
       *
       * @param root the root
       * @param query the query
       * @param cb the cb
       * @return the predicate
       */
      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
         Path<?> path = root.get(this.entityInformation.getIdAttribute());
         this.parameter = cb.parameter(Iterable.class);
         return path.in(new Expression[]{this.parameter});
      }
   }

   /**
    * The Class ExampleSpecification.
    *
    * @param <T> the generic type
    */
   private static class ExampleSpecification<T> implements Specification<T> {
      
      /** The example. */
      private final Example<T> example;

      /**
       * Instantiates a new example specification.
       *
       * @param example the example
       */
      ExampleSpecification(Example<T> example) {
         Assert.notNull(example, "Example must not be null!");
         this.example = example;
      }

      /**
       * To predicate.
       *
       * @param root the root
       * @param query the query
       * @param cb the cb
       * @return the predicate
       */
      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
         return QueryByExamplePredicateBuilder.getPredicate(root, cb, this.example);
      }
   }
}
