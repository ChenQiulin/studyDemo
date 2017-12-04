package study.util;

public enum ModuleType {

	/** 电台:0 */
	RADIO(0),

	/** 消息:1 */
	EVENT(1),

	/** 头像:2 */
	AVATAR(2),

	/** 头像缩略图:3 */
	AVATAR_THUMB(3),

	/** 电台封面:4 */
	RADIO_COVER(4),

	/** 电台封面缩略图:5 */
	RADIO_COVER_THUMB(5),

	/** 节目:6 */
	PROGRAM(6),

	/** 节目封面:7 */
	PROGRAM_COVER(7),

	/** 节目音轨:8 */
	PROGRAM_TRACK(8),

	/** 用户反馈:9 */
	FEEDBACK(9),

	/** 节目封面缩略图:10 */
	PROGRAM_COVER_THUMB(10),

	/** 设备消息:11 */
	DEVICE_EVENT(11),

	/** 聊天消息:12 */
	CHAT_MESSAGE(12),

	/** 专题封面:13 */
	TOPIC_COVER(13),

	/** 专题:14 */
	TOPIC(14),

	/** 专题元素:15 */
	TOPIC_ITEM(15),

	/** 标签:16 */
	TAG(16),

	/** banner:17 */
	BANNER(17),

	/** 页面:18 */
	PAGE(18),

	/** 社区申请:19 */
	SNS_PENDING(19),

	/** 社区信息:20 */
	SNS_INFO(20),

	/** 社区帖子:21 */
	SNS_FEED(21),

	/** 社区帖子评论:22 */
	SNS_COMMENT(22),

	/** 社区封面:23 */
	SNS_COVER(23),

	/** 用户收藏的节目列表:24 */
	PROGRAM_COLLECTION(24),

	/** 节目对应的收藏用户列表:25 */
	PROGRAM_COLLECTOR(25),

	/** 好友列表:26 */
	FRIEND(26),

	/** 专辑:27 */
	ALBUM(27),

	/** 专辑封面:28 */
	ALBUM_COVER(28),

	/** 资金账号:29 */
	ACCOUNT(29),

	/** 订单:30 */
	ORDER(30),

	/** 充值记录:31 */
	RECHARGE(31),

	/** 商品:32 */
	PRODUCT(32),

	/** Widget template:33 */
	WIDGET_TEMPLATE(33),

	/** Widget instance:34 */
	WIDGET_INSTANCE(34),

	/** 交易:35 */
	TRANSACTION(35),

	/** 动态:36 */
	MOMENT(36),

	/** 动态评论:37 */
	MOMENT_COMMENT(37),

	/** 投稿:38 */
	MANUSCRIPT(38),

	/** 投稿音轨:39 */
	MANUSCRIPT_TRACK(39),

	/** 加优申请:40 */
	QUALITY_PENDING(40),

	/** 图片:41 */
	IMAGE(41),

	/** 图片专辑:42 */
	IMAGE_ALBUM(42),

	/** 身份认证:43 */
	RADIO_VERIFY(43),

	/** 用户:44 */
	USER(44),

	/** 电台申请:45 */
	RADIO_PENDING(45),

	/** 广告 */
	ADVERTISING(46),

	/** 节目评论 */
	PROGRAM_COMMENT(47),

	/** 电台新分类 */
	RADIO_LABEL(48),

	/** 主播津贴 */
	NJ_ALLOWANCE(49),

	/** 直播 */
	LIVE(50),

	/** 支付 */
	PAYMENT(51),

	/** 运营后台 */
	OPERATION(52),

	/** 搜索 */
	SEARCH(53),

	/** 公开直播 */
	STUDIO(54),

	/** 抽奖 */
	LUCKY_DRAW(55),

	/** 直播win客户端日志 */
	LIVE_WINDOWS_LOG(56),

	/** 城市电台 */
	CITY_FM(57),

	/** 家族 */
	FAMILY(58),

	/** 红包 */
	RED_ENVELOPE(59),

	/** 群聊 */
	GROUP_CHAT(60),

	/** 设备信息 */
	DEVICE_INFO(61),

	/** 推送消息 */
	PUSH_MSG(62),

	/** 推广活动 */
	PROMOTION(63),

	/** 直播守护团 */
	LIVE_GUARD(64),

	/** 播单 */
	PLAY_SHEET(65),

	/** 付费声音订单 */
	VOICE_ORDER(66),

	/** 内部使用 */
	INTERNAL(511);

	private final int raw;

	private ModuleType(int raw) {
		this.raw = raw;
	}

	public int getRaw() {
		return raw;
	}

	public static ModuleType find(int raw) {
		for (ModuleType type : ModuleType.values()) {
			if (type.getRaw() == raw) {
				return type;
			}
		}
		return null;
	}
}
