package io.github.codexrm.server.dto;

import java.util.ArrayList;
import java.util.List;

public class UserPageDTO {

    private List<UserDTO> userDTOList;
    private PageDTO pageDTO;

    public UserPageDTO() {
        userDTOList = new ArrayList<>();
    }

    public UserPageDTO(List<UserDTO> userList, PageDTO pageDTO) {
        this.userDTOList = userList;
        this.pageDTO = pageDTO;
    }

    public List<UserDTO> getUserDTOList() { return userDTOList; }

    public void setUserDTOList(List<UserDTO> userDTOList) { this.userDTOList = userDTOList; }

    public PageDTO getPageDTO() { return pageDTO; }

    public void setPageDTO(PageDTO pageDTO) { this.pageDTO = pageDTO; }
}
