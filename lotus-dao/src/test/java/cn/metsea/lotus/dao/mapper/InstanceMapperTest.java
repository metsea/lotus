package cn.metsea.lotus.dao.mapper;

import cn.metsea.lotus.common.enums.JobInstanceStatusEnum;
import cn.metsea.lotus.dao.entity.Instance;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Job Instance Mapper Test
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
@Rollback()
public class InstanceMapperTest {
    @Autowired
    private InstanceMapper instanceMapper;

    @Test
    public void TestInsert(){
        Instance  instance = new Instance();
        instance.setJobId(10);
        StringBuilder config = new StringBuilder();
        config.append("{")
            .append("\"sql\":").append("\"select * from job\"")
            .append("\"variates\":[")
            .append("{")
            .append("\"key1\":").append("\"key\",")
            .append("\"val1\":").append("\"value1\"")
            .append("}")
            .append("]")
            .append("\"arguments\":[")
            .append("{")
            .append("\"key1\":").append("\"key\",")
            .append("\"val1\":").append("\"value1\"")
            .append("}")
            .append("]")
            .append("}");
        instance.setConfig(config.toString());
        instance.setStatus(JobInstanceStatusEnum.UNKNOWN);

        int insert = instanceMapper.insert(instance);
        Assert.assertEquals(insert,1);
    }

    @Test
    public void testUpdate(){
        Instance  instance = new Instance();
        instance.setId(1L);
        instance.setStatus(JobInstanceStatusEnum.RUNNING);
        int updateById = instanceMapper.updateById(instance);
        Assert.assertEquals(updateById,1);
    }

    @Test
    public void testDelete(){
        int deleteById = instanceMapper.deleteById(1L);
        Assert.assertEquals(deleteById,1);
    }

    @Test
    public void testQuery(){
        Instance instance = instanceMapper.selectById(4L);
        Assert.assertNotNull(instance);
    }

    @Test
    public void testQueryList(){
        Set set = new TreeSet<Integer>();
        set.add(1);
        set.add(2);
        List list = instanceMapper.selectBatchIds(set);
        Assert.assertNotNull(list);
    }

    @Test
    public void testQueryPage(){
        Page<Instance> page = new Page(1,2);
        QueryWrapper wrapper = new QueryWrapper<Instance>();
        wrapper.orderByDesc("id");
        Page page1 = instanceMapper.selectPage(page, wrapper);
        List<Instance> records = page1.getRecords();
        Assert.assertNotNull(records);
    }
}
