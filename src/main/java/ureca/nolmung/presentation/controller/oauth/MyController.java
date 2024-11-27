package ureca.nolmung.presentation.controller.oauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MyController {

    @RequestMapping("/test")
    @ResponseBody  // 뷰를 반환하지 않고 JSON 응답을 반환
    public Map<String, String> showTestPage() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "테스트 페이지에 오신 것을 환영합니다!");
        return response;
    }
}
