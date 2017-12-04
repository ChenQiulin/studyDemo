package study.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 全局ID生成器第二版<br>
 * 位数分布：符号位（1位，不使用），版本位（2位，固定为1），时间标志（秒级，30位），serverId（10位, 0-1023），计数器（12位,
 * 0-4095），类型（9位, 0-511）
 * 
 */
public class GuidBuilderV2 {

	public static final int VERSION = 1;

	private final AtomicLong idCounter = new AtomicLong();

	private final long timeFlag;
	private final int serverId;
	private final int counterLimit;
	private final int typeLimit;

	protected GuidBuilderV2(int serverId) {
		Date old = baseTime();
		timeFlag = old.getTime();
		idCounter.set(0);

		int limit = (1 << 10) - 1;
		if (serverId > limit) {
			throw new RuntimeException("Server id must be in range of 0-" + limit);
		}

		this.serverId = serverId;
		this.counterLimit = (1 << 12) - 1;
		this.typeLimit = (1 << 9) - 1;
	}

	private Date baseTime() {
		Calendar c = Calendar.getInstance();
		// Calendar中月从0开始，所以1月是0
		c.set(2013, 0, 1, 0, 0, 0);
		return c.getTime();
	}

	private long counter() {
		return idCounter.getAndIncrement() % (counterLimit + 1);
	}

	private long timeFlag() {
		return (System.currentTimeMillis() - timeFlag) / 1000L;
	}

	private long timeFlag(Date date) {
		return (date.getTime() - timeFlag) / 1000L;
	}

	private int serverFlag() {
		return serverId;
	}

	/**
	 * 生产一个ID<br>
	 * ID长度为64位，具体位分布为：<br>
	 * 符号位（1位，不使用），版本位（2位，固定为1），时间标志（秒级，30位），serverId（10位, 0-1023），计数器（12位,
	 * 0-4095），类型（9位, 0-511）
	 * 
	 * @param type
	 *            对象类型
	 * @deprecated
	 */
	protected long nextId(int type) {
		if (type > typeLimit) {
			throw new RuntimeException("Type must be in range of 0-" + typeLimit);
		}

		return (((VERSION << 30 | timeFlag()) << 10 | serverFlag()) << 12 | counter()) << 9 | type;
	}

	/**
	 * 生产一个ID<br>
	 * ID长度为64位，具体位分布为：<br>
	 * 符号位（1位，不使用），版本位（2位，固定为1），时间标志（秒级，30位），serverId（10位, 0-1023），计数器（12位,
	 * 0-4095），类型（9位, 0-511）
	 * 
	 * @param type
	 *            对象类型
	 */
	protected long nextId(ModuleType type) {
		if (type.getRaw() > typeLimit) {
			throw new RuntimeException("Type must be in range of 0-" + typeLimit);
		}

		return (((VERSION << 30 | timeFlag()) << 10 | serverFlag()) << 12 | counter()) << 9 | type.getRaw();
	}

	/**
	 * 生产一个ID<br>
	 * ID长度为64位，具体位分布为：<br>
	 * 符号位（1位，不使用），版本位（2位，固定为1），时间标志（秒级，30位），serverId（10位, 0-1023），计数器（12位,
	 * 0-4095），类型（9位, 0-511）
	 *
	 * @param type
	 *            对象类型
	 */
	protected long nextId(ModuleType type, Date date) {
		if (type.getRaw() > typeLimit) {
			throw new RuntimeException("Type must be in range of 0-" + typeLimit);
		}

		return (((VERSION << 30 | timeFlag(date)) << 10 | serverFlag()) << 12 | counter()) << 9 | type.getRaw();
	}

	/**
	 * 从ID重解出版本
	 * 
	 * @param id
	 * @return
	 */
	protected int extractVersion(long id) {
		return (int) (id >>> 61);
	}

	/**
	 * 从ID中解出时间标志
	 * 
	 * @param id
	 * @return
	 */
	protected long extractTime(long id){
        long i =  id << 3 >>> 34;
        return id << 3 >>> 34;
	}

	/**
	 * 从ID中解出服务器ID
	 * 
	 * @param id
	 * @return
	 */
	protected int extractServerId(long id) {
		return (int) (id << 33 >>> 54);
	}

	/**
	 * 从ID中解出对象类型
	 * 
	 * @param id
	 * @return
	 */
	protected int extractType(long id) {
		return (int) id & 0x1FF;
	}

	/**
	 * 从ID中解出计数器
	 * 
	 * @param id
	 * @return
	 */
	protected int extractCounter(long id) {
		return (int) (id << 43 >>> 52);
	}

	/**
	 * 从ID中解出时间标志并转换成实际的时间戳。 <font color="red">此方法解出的数据只精确到秒级，需要到毫秒级的，请使用
	 * {@link #extractRealTimeStampMillis(long)}</font>
	 *
	 * @deprecated
	 * @param id
	 * @return
	 * @see #extractRealTimeStampMillis(long)
	 */
	@Deprecated
	protected long extractRealTimeStamp(long id) {
		return (extractTime(id) * 1000L + timeFlag) / 1000L;
	}

	/**
	 * 从ID中解出时间标志并转换成实际的时间戳（毫秒）</font>
	 * 
	 * @param id
	 * @return
	 */
	protected long extractRealTimeStampMillis(long id) {
		return extractTime(id) * 1000L + timeFlag;
	}

	public static void main(String[] args) {
        GuidBuilderV2 fac = new GuidBuilderV2(1000);
        System.out.println(fac.nextId(ModuleType.PROGRAM));
		long id = 2638698503912618542L;
		System.out.println("id: " + id);
		System.out.println("ver: " + fac.extractVersion(id));
		System.out.println("server_id: " + fac.extractServerId(id));
		System.out.println("type: " + fac.extractType(id));
		System.out.println("counter: " + fac.extractCounter(id));
		System.out.println("real_time: "
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(fac.extractRealTimeStampMillis(id))));

//		System.out.println();
//
//		long id1 = fac.nextId(0);
//		System.out.println("id: " + id1);
//		System.out.println("ver: " + fac.extractVersion(id1));
//		System.out.println("server_id: " + fac.extractServerId(id1));
//		System.out.println("type: " + fac.extractType(id1));
//		System.out.println("counter: " + fac.extractCounter(id1));
//		System.out.println("real_time: "
//				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(fac.extractRealTimeStampMillis(id1))));
//
//		System.out.println();
//
//		long id2 = fac.nextId(511);
//		System.out.println("id: " + id2);
//		System.out.println("ver: " + fac.extractVersion(id2));
//		System.out.println("server_id: " + fac.extractServerId(id2));
//		System.out.println("type: " + fac.extractType(id2));
//		System.out.println("counter: " + fac.extractCounter(id2));
//		System.out.println("real_time: "
//				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(fac.extractRealTimeStampMillis(id2))));
//
//		System.out.println();
//
//		id1 = fac.nextId(ModuleType.USER);
//		id2 = fac.nextId(44);
//		System.out.println("type1: " + fac.extractType(id1) + "  type2: " + fac.extractType(id2));
//	}
    }

}
