package com.example.healthcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    
    @GetMapping("/")
    public String home() {
        return """
            <html>
            <head>
                <title>Health Check Dashboard</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 40px; }
                    .endpoint { background: #f5f5f5; padding: 10px; margin: 10px 0; border-radius: 5px; }
                    .method { color: #007bff; font-weight: bold; }
                    .url { color: #28a745; font-family: monospace; }
                </style>
            </head>
            <body>
                <h1>🏥 Health Check Dashboard API</h1>
                <p>Bu API aşağıdaki endpoint'leri sunar:</p>
                
                <div class="endpoint">
                    <span class="method">GET</span> 
                    <span class="url">/ping</span> - Basit ping kontrolü
                </div>
                
                <div class="endpoint">
                    <span class="method">GET</span> 
                    <span class="url">/api/status</span> - Tüm modüllerin durumu
                </div>
                
                <p><strong>Test etmek için:</strong></p>
                <ul>
                    <li><a href="/ping">/ping</a></li>
                    <li><a href="/api/status">/api/status</a></li>
                </ul>
            </body>
            </html>
            """;
    }
    
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
} 