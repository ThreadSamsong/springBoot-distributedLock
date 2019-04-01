package com.samsong.distributedlock.distributedlock.redis;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * Redisson工具类
 * @author samsong
 *
 */
public class RedissonDistributedLocker implements DistributedLocker{

	@Autowired
    private RedissonClient redissonClient;  //RedissonClient已经由配置类生成，这里自动装配即可
	
	//直接加锁，获取不到锁则一直等待获取锁
	@Override
	public RLock lock(String lockKey) {
		RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
	}

	//直接加锁，获取不到锁则一直等待获取锁
	@Override
	public RLock lock(String lockKey, long timeout) {
		RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, TimeUnit.SECONDS);
        return lock;
	}

	//直接加锁，获取不到锁则一直等待获取锁
	@Override
	public RLock lock(String lockKey, TimeUnit unit, long timeout) {
		RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
	}

	//尝试获取锁，等待waitTime，自己获得锁后一直不解锁则leaseTime后自动解锁
	@Override
	public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) {
		RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }

	}

	@Override
	public void unlock(String lockKey) {
		RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
	}

	@Override
	public void unlock(RLock lock) {
		lock.unlock();
	}
	

}
