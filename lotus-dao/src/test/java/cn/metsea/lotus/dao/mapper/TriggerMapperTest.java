package cn.metsea.lotus.dao.mapper;

import cn.metsea.lotus.dao.entity.Trigger;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * Schedule Instance Mapper Test
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
@Rollback()
public class TriggerMapperTest {

    @Autowired
    private TriggerMapper triggerMapper;

    @Test
    public void TestInsert() {
        Trigger trigger = new Trigger();
        trigger.setJobId(10);
        trigger.setConfig("{}");
        int insert = triggerMapper.insert(trigger);
        Assert.assertEquals(insert, 1);
    }

    @Test
    public void testUpdate() {
        Trigger trigger = new Trigger();
        trigger.setId(1);
        int updateById = triggerMapper.updateById(trigger);
        Assert.assertEquals(updateById, 1);
    }

    @Test
    public void testDelete() {
        int deleteById = triggerMapper.deleteById(2);
        Assert.assertEquals(deleteById, 1);
    }

    @Test
    public void testQuery() {
        Trigger trigger = triggerMapper.selectById(3);
        Assert.assertNotNull(trigger);
    }

    @Test
    public void testQueryList() {
        Set set = new TreeSet<Integer>();
        set.add(2);
        set.add(3);
        List list = triggerMapper.selectBatchIds(set);
        Assert.assertNotNull(list);
    }

    @Test
    public void testQueryPage() {
        Page<Trigger> page = new Page(1, 2);
        QueryWrapper wrapper = new QueryWrapper<Trigger>();
        wrapper.orderByDesc("id");
        Page page1 = triggerMapper.selectPage(page, wrapper);
        List<Trigger> records = page1.getRecords();
        Assert.assertNotNull(records);
    }
}
