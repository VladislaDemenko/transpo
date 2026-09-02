package java.com.transpolink.controller;

import java.com.transpolink.dto.CalculatorRequest;
import java.com.transpolink.dto.ConsultationRequest;
import java.com.transpolink.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DeliveryController {

    private final EmailService emailService;

    /**
     * Обработка формы калькулятора (#calculator)
     */
    @PostMapping("/calculator")
    public ResponseEntity<Map<String, String>> calculate(@Valid @RequestBody CalculatorRequest request) {
        log.info("📥 Получена заявка из калькулятора: {}", request);
        emailService.sendCalculatorRequest(request);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Заявка отправлена! Менеджер свяжется с вами в течение 15 минут.");
        return ResponseEntity.ok(response);
    }

    /**
     * Обработка формы консультации (#request)
     */
    @PostMapping("/consultation")
    public ResponseEntity<Map<String, String>> consultation(@Valid @RequestBody ConsultationRequest request) {
        log.info("📥 Получена заявка на консультацию: {}", request);
        emailService.sendConsultationRequest(request);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Заявка отправлена! Мы свяжемся с вами в ближайшее время.");
        return ResponseEntity.ok(response);
    }

    /**
     * Health-check
     */
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}