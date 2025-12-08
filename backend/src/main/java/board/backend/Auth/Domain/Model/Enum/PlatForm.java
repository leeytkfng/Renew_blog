package board.backend.Auth.Domain.Model.Enum;

public enum PlatForm {
    NORMAL("일반 로그인"),
    OAUTH("소셜 로그인"),
    ADMIN("관리자 로그인"),
    ANONYMOUS("익명(비회원) 로그인");

    private String plat;

    PlatForm(String plat) {
        this.plat =  plat;
    }

}



