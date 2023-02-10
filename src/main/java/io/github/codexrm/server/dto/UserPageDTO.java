package io.github.codexrm.server.dto;

import java.util.ArrayList;
import java.util.List;

public class UserPageDTO {

    private List<UserDTO> userList;
    private PageDTO pageDTO;

    public UserPageDTO() {
        userList = new ArrayList<>();
    }

    public UserPageDTO(List<UserDTO> userList, PageDTO pageDTO) {
        this.userList = userList;
        this.pageDTO = pageDTO;
    }

    public List<UserDTO> getUserList() { return userList; }

    public void setUserList(List<UserDTO> userList) { this.userList = userList; }

    public PageDTO getPageDTO() { return pageDTO; }

    public void setPageDTO(PageDTO pageDTO) { this.pageDTO = pageDTO; }
}
