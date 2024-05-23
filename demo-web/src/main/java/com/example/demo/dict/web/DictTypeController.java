package com.example.demo.dict.web;

import com.example.demo.dict.api.DictTypeService;
import com.example.demo.dict.api.bo.DictType;
import com.example.demo.framework.annotation.Log;
import com.example.demo.framework.response.ListResponse;
import com.example.demo.framework.response.ObjectResponse;
import com.example.demo.framework.web.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/system/dict/type")
public class DictTypeController extends BaseController {

    @Autowired
    private DictTypeService dictTypeService;

    @PreAuthorize("hasAuthority('dict:crud')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ListResponse<DictType> list(HttpServletRequest request, HttpServletResponse response) {
        DictType dictType = this.getParameter(request, new DictType());

        dictType.setName(this.getParameter(request, "name"));
        dictType.setValue(this.getParameter(request, "value"));
        dictType.setStatus(this.getParameter(request, "status"));

        int count = dictTypeService.countDictType(dictType);

        if (count == 0) {
            return new ListResponse<>(0, null);
        }

        return new ListResponse<>(count, dictTypeService.listDictTypes(dictType));
    }

    @PreAuthorize("hasAuthority('dict:crud')")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ObjectResponse<DictType> get(HttpServletRequest request, HttpServletResponse response) {
        String id = this.getParameter(request, "id");
        String value = this.getParameter(request, "type");
        return new ObjectResponse<>(dictTypeService.getDictType(id, value));
    }

    @Log(module = "")
    @PreAuthorize("hasAuthority('dict:crud')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ObjectResponse<DictType> save(HttpServletRequest request, HttpServletResponse response) {
        DictType dictType = this.getParameter(request, DictType.class);
        return new ObjectResponse<>(
            dictTypeService.insertDictType(dictType, this.getUser().getName()));
    }

    @Log(module = "")
    @PreAuthorize("hasAuthority('dict:crud')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ObjectResponse<DictType> update(HttpServletRequest request,
                                           HttpServletResponse response) {
        DictType dictType = this.getParameter(request, DictType.class);
        return new ObjectResponse<>(
            dictTypeService.updateDictType(dictType.getId(), dictType, this.getUser().getName()));
    }

    @Log(module = "")
    @PreAuthorize("hasAuthority('dict:crud')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ObjectResponse<DictType> delete(HttpServletRequest request,
                                           HttpServletResponse response) {
        DictType dictType = this.getParameter(request, DictType.class);
        return new ObjectResponse<>(
            dictTypeService.deleteDictType(dictType.getId(), this.getUser().getName()));
    }

}