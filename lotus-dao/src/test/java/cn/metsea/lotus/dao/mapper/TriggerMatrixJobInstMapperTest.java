package cn.metsea.lotus.dao.mapper;

import cn.metsea.lotus.dao.entity.TriggerMatrixJobInst;
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
public class TriggerMatrixJobInstMapperTest {

    @Autowired
    private TriggerMatrixJobInstMapper triggerMatrixJobInstMapper;

    @Test
    public void TestInsert() {
        TriggerMatrixJobInst triggerMatrixJobInst = new TriggerMatrixJobInst();
        triggerMatrixJobInst.setJobId(1);
        triggerMatrixJobInst.setName("A1");
        triggerMatrixJobInst.setFireTime("02:00:00");
        int insert = triggerMatrixJobInstMapper.insert(triggerMatrixJobInst);
        Assert.assertEquals(insert, 1);
    }

    @Test
    public void testUpdate() {
        TriggerMatrixJobInst triggerMatrixJobInst = new TriggerMatrixJobInst();
        triggerMatrixJobInst.setId(2);
        triggerMatrixJobInst.setFireTime("03:00:00");
        int updateById = triggerMatrixJobInstMapper.updateById(triggerMatrixJobInst);
        Assert.assertEquals(updateById, 1);
    }

    @Test
    public void testDelete() {
        int deleteById = triggerMatrixJobInstMapper.deleteById(2);
        Assert.assertEquals(deleteById, 1);
    }

    @Test
    public void testQuery() {
        TriggerMatrixJobInst triggerMatrixJobInst = triggerMatrixJobInstMapper.selectById(3);
        Assert.assertNotNull(triggerMatrixJobInst);
    }

    @Test
    public void testQueryList() {
        Set set = new TreeSet<Integer>();
        set.add(2);
        set.add(3);
        List list = triggerMatrixJobInstMapper.selectBatchIds(set);
        Assert.assertNotNull(list);
    }

    @Test
    public void testQueryPage() {
        Page<TriggerMatrixJobInst> page = new Page(1, 2);
        QueryWrapper wrapper = new QueryWrapper<TriggerMatrixJobInst>();
        wrapper.orderByDesc("id");
        Page page1 = triggerMatrixJobInstMapper.selectPage(page, wrapper);
        List<TriggerMatrixJobInst> records = page1.getRecords();
        Assert.assertNotNull(records);
    }
}
