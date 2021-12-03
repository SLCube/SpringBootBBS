# SpringBootBBS
    시작일 : 2021년 12월 01일 
    스프링부트를 이용한 게시판 프로젝트 따라하기
---
## 참고 사이트

1. 기본 틀 : https://congsong.tistory.com/category/Spring%20Boot

1. Spring DI/IoC 이해 참고 사이트 : https://kingofbackend.tistory.com/41

1. eclipse에서 HTML, JSP파일 자동정렬시 가동성을 조금 더 높이는 방법 : https://12teamtoday.tistory.com/50

1. Gradle project Import 할때 주의 할점 : https://kku-jun.tistory.com/12

1. Java 8 LocalDateTime 직렬화 역직렬화 오류 해결 방법 : https://itpro.tistory.com/117

1. HandlerInterceptorAdapter deprecated 해결 방법 : https://oingdaddy.tistory.com/399

1. TransactionInterceptor deprecated 해결 방법 : https://earth-95.tistory.com/44

1. REST API 설계 규칙 참고 사이트 : https://congsong.tistory.com/30?category=749196

1. Gson 라이브러리 추가 '기본 틀'과 다른점 참고 사이트 : https://congsong.tistory.com/30?category=749196 의 댓글 (버전은 변경 가능)
    ```
    implementation group: 'com.google.code.gson', name: 'gson', version: '2.7' 
    ```

1. 댓글 리스트 구현 중 Advanced REST client 사용해보기 (본문 중 5번) 중 오류가 있을 시 
본문 중 6번(JSON날짜 데이터 형태 지정하기) 까지 진행후 5번 실행하면 오류가 없습니다 : https://congsong.tistory.com/30?category=749196

1. 댓글 입력 구현 중 Could not read JSON: Unable to make field private final java.time.LocalDate java.time.LocalDateTime.date accessible 해결 방법 : https://congsong.tistory.com/32?category=749196 의 댓글(월드 러브님)

```
    /* GsonLocalDateTimeAdapter.java에 @Configuration추가 및 Gson을 Bean으로 등록 */

    @Configuration
    public class GsonLocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

        @Bean
        public Gson gson() {
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new      GsonLocalDateTimeAdapter()).create();
            return gson;
        }
    }
```

```
    /* 이후 CommentController.java에 Gson을 주입 */

    @Autowired
	private Gson gson;
```

```
    /* CommentController.java에 getCommentList method를 다음과 같이 변경 */

    @GetMapping(value = "/comments/{boardIdx}")
	public JsonObject getCommentList(@PathVariable("boardIdx") Long boardIdx,
			@ModelAttribute("params") CommentDTO params) {

		JsonObject jsonObj = new JsonObject();

		List<CommentDTO> commentList = commentService.getCommentList(params);
		if (CollectionUtils.isEmpty(commentList) == false) {
            /* Gson을 주입했기 때문에 이곳에 Gson선언이 빠짐 */
			JsonArray jsonArr = gson.toJsonTree(commentList).getAsJsonArray();
			jsonObj.add("commentList", jsonArr);
		}

		return jsonObj;
	}
```