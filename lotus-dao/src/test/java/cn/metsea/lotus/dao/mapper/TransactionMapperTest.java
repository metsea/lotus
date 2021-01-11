package cn.metsea.lotus.dao.mapper;

import cn.metsea.lotus.dao.entity.Transaction;
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
public class TransactionMapperTest {

    @Autowired
    private TransactionMapper transactioneMapper;

    @Test
    public void TestInsert() {
        Transaction transaction = new Transaction();

        transaction.setDag("{\"edge\":[{\"source\":1,\"target\":3},{\"source\":2,\"target\":3}]}");
        transaction.setConfig("[{\"source\":1,\"target\":3},{\"source\":2,\"target\":3}]");
        int insert = transactioneMapper.insert(transaction);
        Assert.assertEquals(insert, 1);
    }

    @Test
    public void testUpdate() {
        Transaction transaction = new Transaction();
        transaction.setId(2L);
        int updateById = transactioneMapper.updateById(transaction);
        Assert.assertEquals(updateById, 1);
    }

    @Test
    public void testDelete() {
        int deleteById = transactioneMapper.deleteById(2);
        Assert.assertEquals(deleteById, 1);
    }

    @Test
    public void testQuery() {
        Transaction transaction = transactioneMapper.selectById(3);
        Assert.assertNotNull(transaction);
    }

    @Test
    public void testQueryList() {
        Set set = new TreeSet<Integer>();
        set.add(2);
        set.add(3);
        List list = transactioneMapper.selectBatchIds(set);
        Assert.assertNotNull(list);
    }

    @Test
    public void testQueryPage() {
        Page<Transaction> page = new Page(1, 2);
        QueryWrapper wrapper = new QueryWrapper<Transaction>();
        wrapper.orderByDesc("id");
        Page page1 = transactioneMapper.selectPage(page, wrapper);
        List<Transaction> records = page1.getRecords();
        Assert.assertNotNull(records);
    }
}
