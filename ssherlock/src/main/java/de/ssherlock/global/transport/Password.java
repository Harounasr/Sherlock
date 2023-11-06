package de.ssherlock.global.transport;

public record Password(
        String hash,
        String salt
) {
    public void builder() {
        LoginInfo loginInfo = new LoginInfo.Builder().password(new Password("hi", "hl")).username("no").build();
    }
}
