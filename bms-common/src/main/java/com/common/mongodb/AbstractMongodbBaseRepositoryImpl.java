package com.common.mongodb;

import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public abstract class AbstractMongodbBaseRepositoryImpl<T> implements MongodbBaseRepository<T> {

	@Autowired
	private MongoTemplate mongoTemplate;

	public MongoTemplate getMongoTemplate() {
		return this.mongoTemplate;
	}

	@Override
	public List<T> findAll(final Class<T> entityClass) {
		return (List<T>) this.getMongoTemplate().findAll((Class) entityClass);
	}

	@Override
	public List<T> findAll(final Map<String, Object> params, final Class<T> entityClass) {
		final Query query = this.createQuery(params);
		return (List<T>) this.getMongoTemplate().find(query, (Class) entityClass);
	}

	@Override
	public T findOne(final Map<String, Object> params, final Class<T> entityClass) {
		final Query query = this.createQuery(params);
		return (T) this.getMongoTemplate().findOne(query, (Class) entityClass);
	}

	@Override
	public void save(final T entity) {
		this.getMongoTemplate().insert(entity);
	}

	public void save(final T entity, final String tenant) {
		this.getMongoTemplate().insert(entity, tenant);
	}

	@Override
	public WriteResult remove(final Map<String, Object> params, final Class<T> entityClass) {
		final Query query = this.createQuery(params);
		return this.getMongoTemplate().remove(query, entityClass);
	}

	@Override
	public T findAndModify(final Map<String, Object> params, final Map<String, Object> setParams,
			final Class<T> entityClass) {
		final Query query = this.createQuery(params);
		final Update update = this.createUpdate(setParams);
		return (T) this.getMongoTemplate().findAndModify(query, update, (Class) entityClass);
	}

	@Override
	public WriteResult findAndModifyAll(final Map<String, Object> params, final Map<String, Object> setParams,
			final Class<T> entityClass) {
		final Query query = this.createQuery(params);
		final Update update = this.createUpdate(setParams);
		return this.getMongoTemplate().updateMulti(query, update, entityClass);
	}

	private Query createQuery(final Map<String, Object> params) {
		final Query query = new Query();
		Criteria criteria = null;
		int count = 0;
		for (final Map.Entry<String, Object> param : params.entrySet()) {
			String key = param.getKey();
			if (count == 0) {
				if (key.startsWith("or")) {
					key = key.replace("or", "");
					String value = (String) param.getValue();
					String[] ss = value.split(",");
					criteria = new Criteria().orOperator(Criteria.where(key).is(ss[0]), Criteria.where(key).is(ss[1]));
				} else if (key.startsWith("elem")) {
					key = key.replace("elem", "");
					String value = (String) param.getValue();
					String[] ss = value.split(",");
					Criteria cri = Criteria.where(ss[0]).is(ss[1]);
					criteria = Criteria.where(key).elemMatch(cri);
				} else {
					criteria = Criteria.where(param.getKey()).is(param.getValue());
				}

			} else {
				if (key.startsWith("or")) {
					key = key.replace("or", "");
					String value = (String) param.getValue();
					String[] ss = value.split(",");
					criteria.orOperator(Criteria.where(key).is(value.split(ss[0])),
							Criteria.where(key).is(value.split(ss[1])));
				} else if (key.startsWith("elem")) {
					key = key.replace("elem", "");
					String value = (String) param.getValue();
					String[] ss = value.split(",");
					Criteria cri = Criteria.where(ss[0]).is(ss[1]);
					criteria.andOperator(Criteria.where(key).elemMatch(cri));
				} else {
					criteria.and(param.getKey()).is(param.getValue());
				}
			}
			++count;
		}
		query.addCriteria(criteria);
//		log.info("query:{}", query.toString().length() > 800 ? "长度过长，省略打印。" : query);
		return query;
	}

	private Update createUpdate(final Map<String, Object> params) {
		final Update update = new Update();
		for (final Map.Entry<String, Object> param : params.entrySet()) {
			update.set(param.getKey(), param.getValue());
		}
		return update;
	}

	@Override
	public T findOneSortAsc(final Map<String, Object> params, final Class<T> entityClass) {
		final Query query = this.createQuery(params);
		return (T) this.getMongoTemplate()
				.findOne(query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "timestamp"))), (Class) entityClass);
	}

	@Override
	public T findOneSortDesc(final Map<String, Object> params, final Class<T> entityClass) {
		final Query query = this.createQuery(params);
		return (T) this.getMongoTemplate()
				.findOne(query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "timestamp"))), (Class) entityClass);
	}

}
