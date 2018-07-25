package kr.zchat.chat;

public class ChatCmd {
	
	/**
	 * 기본채널
	 */
    public static final Integer CHANNEL_ID = 100001;

    
    /**
     * 채널 연결 완료
     */
    public static final String CHANNEL_CONNECT_FINISH  = "CHANNEL_CONNECT_FINISH";	
    
    /**
     * 채널정보
     */
    public static final String CHANNEL_INFO = "CHANNEL_INFO";	
    
    /**
     * 채널생성
     */
    public static final String CHANNEL_ADD = "CHANNEL_ADD";
    
    /**
     * 채널퇴장
     */
    public static final String CHANNEL_OUT = "CHANNEL_OUT";
    
    /**
     * 채널입장
     */
    public static final String CHANNEL_IN = "CHANNEL_IN";
    
    /**
     * 채널목록
     */
    public static final String CHANNEL_LIST = "CHANNEL_LIST";
    
    /**
     * 채널생성
     */
    public static final String CHANNEL_CREATE = "CHANNEL_CREATE";
    
    /**
     * 채널이동
     */
    public static final String CHANNEL_CHANGE = "CHANNEL_CHANGE";
    
    /**
     * 채널이동 완료
     */
    public static final String CHANNEL_CHANGE_FINISH = "CHANNEL_CHANGE_FINISH";
    
    /**
     * 유저메세지
     */
    public static final String USER_MSG = "USER_MSG";
    
    /**
     * 유저목록
     */
    public static final String USER_LIST = "USER_LIST";
    
    /**
     * 닉변경
     */
    public static final String NICK_CHANGE = "NICK_CHANGE";
    
    /**
     * 닉변경 완료
     */
    public static final String NICK_CHANGE_FINISH  = "NICK_CHANGE_FINISH";
    
    /**
     * 비밀번호 생성
     */
    public static final String PASSWORD_CREATE  = "PASSWORD_CREATE";

    /**
     * 비밀번호 생성완료
     */
	public static final String CREATE_PASSWORD_FINISH = "CREATE_PASSWORD_FINISH";
	
	/**
	 * 비밀번호 유무
	 */
	public static final String IS_PASSWORD = "IS_PASSWORD";
	
	/**
	 * 비밀번호 유무
	 */
	public static final String FALSE_PASSWORD = "FALSE_PASSWORD";
}
