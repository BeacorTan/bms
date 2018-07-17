package com.test.mongodb;

import com.alibaba.fastjson.JSON;
import com.common.mongodb.BaseMongoRepository;
import com.mongodb.WriteResult;
import com.test.AuthApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseMongoRepositoryTest extends AuthApplicationTests {

	@Autowired
	private BaseMongoRepository<Demo> demoRepository;

    /*@Autowired
    private BaseMongoRepository<LoanInfoApplyEntity> applyRepository;*/

	@Test
	public void save() {
		Demo Demo = new Demo();
		Demo.setUuid("cccccccc");
		Demo.setName("sssssss");
		demoRepository.save(Demo);
	}

    @Test
    public void saveList() {
	    List<Demo> demos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Demo demo = new Demo();
            demo.setUuid("cccccccc" + i);
            demo.setName("sssssss" + i);
            demos.add(demo);
        }
        demoRepository.saveList(demos);
    }

	@Test
	public void findOne() {
		Map<String, Object> params = new HashMap<>();
		params.put("uuid", "ehruhgerbefsubfuwerui");
		Demo findOne = demoRepository.findOne(params, Demo.class);
		System.out.println(JSON.toJSONString(findOne));
	}

	@Test
	public void findAll() {
		List<Demo> findAll = demoRepository.findAll(Demo.class);
		System.out.println(JSON.toJSONString(findAll));
	}

	@Test
	public void findAllWithParam() {
		Map<String, Object> params = new HashMap<>();
		params.put("uuid", "cccccccc");
		List<Demo> findAll = demoRepository.findAll(params, Demo.class);
		System.out.println(JSON.toJSONString(findAll));
	}

	/*@Test
	public void findAllWithOrParam() {
		Map<String, Object> params = new HashMap<>();
		params.put("orappStatus", "01,02");
		List<LoanInfoApplyEntity> findAll = applyRepository.findAll(params, LoanInfoApplyEntity.class);
		System.out.println("findAll:" + JSON.toJSONString(findAll));
	}*/

    /*@Test
    public void findAllWithElemParam() {
        Map<String, Object> params = new HashMap<>();
        params.put("appStatus", "02");
        params.put("elemrequestSuccessInfo", "status,0");
        List<LoanInfoApplyEntity> findAll = applyRepository.findAll(params, LoanInfoApplyEntity.class);
        System.out.println("findAll:" + JSON.toJSONString(findAll));
    }*/

	@Test
	public void findAndModify() {
		Map<String, Object> params = new HashMap<>();
		params.put("uuid", "cccccccc");
		Map<String, Object> updateParams = new HashMap<>();
		updateParams.put("name", "1221212555");
		// 返回原值
		Demo findOne = demoRepository.findAndModify(params, updateParams, Demo.class);
		System.out.println(JSON.toJSONString(findOne));
		// 值已经更新
		Demo findOne1 = demoRepository.findOne(params, Demo.class);
		System.out.println(JSON.toJSONString(findOne1));
	}

	@Test
	public void findAndModifyAll() {
		Map<String, Object> params = new HashMap<>();
		params.put("uuid", "cccccccc1");
		Map<String, Object> updateParams = new HashMap<>();
		updateParams.put("name", "1221212555");
		// 返回原值
		WriteResult findAndModifyAll = demoRepository.findAndModifyAll(params, updateParams, Demo.class);
		System.out.println(JSON.toJSONString(findAndModifyAll));
	}
	
	@Test
	public void findByModfiy() {
		Map<String, Object> params = new HashMap<>();
		params.put("uuid", "ehruhgerbefsubfuwerui");
		Map<String, Object> updateParams = new HashMap<>();
		updateParams.put("name", "12212126666");
		// 返回原值
		Demo findOne = demoRepository.findByModfiy(params, updateParams, Demo.class);
		System.out.println(JSON.toJSONString(findOne));
		// 值已经更新
		Demo findOne1 = demoRepository.findOne(params, Demo.class);
		System.out.println(JSON.toJSONString(findOne1));
	}

	@Test
	public void findAndModifyForPush() {
		Map<String, Object> params = new HashMap<>();
		params.put("uuid", "ehruhgerbefsubfuwerui");
		Map<String, Object> updateParams = new HashMap<>();
		List<String> list = new ArrayList<String>();
		list.add("6666666");
		list.add("777777");
		updateParams.put("info", list);
		//
		Demo findOne = demoRepository.findAndModifyForPush(params, updateParams, Demo.class);
		System.out.println(JSON.toJSONString(findOne));
		// 值已经更新
		Demo findOne1 = demoRepository.findOne(params, Demo.class);
		System.out.println(JSON.toJSONString(findOne1));
	}

	@Test
	public void remove() {
		Map<String, Object> params = new HashMap<>();
		params.put("uuid", "cccccccc");
		WriteResult remove = demoRepository.remove(params, Demo.class);
		System.out.println(JSON.toJSONString(remove));
	}
}
