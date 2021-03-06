package com.cglee079.changoos.dao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.cglee079.changoos.model.BlogVo;
import com.cglee079.changoos.model.BoardComtVo;
import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.BoardImageVo;

@Transactional
@Rollback(true)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/test_config/dao-context.xml",
	"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BlogDaoTest {
	
	@Autowired private BlogDao blogDao;
	@Autowired private BoardComtDao boardComtDao;
	@Autowired private BoardFileDao boardFileDao;
	@Autowired private BoardImageDao boardImageDao;
	
	@Value("#{db['blog.file.tb.name']}") private String fileTB;
	@Value("#{db['blog.image.tb.name']}") private String imageTB;
	@Value("#{db['blog.comt.tb.name']}")	private String comtTB;
	
	private BlogVo sampleBlogA;
	private BlogVo sampleBlogB;
	private BlogVo sampleBlogC;

	@Before
	public void setUp() {
		sampleBlogA = BlogVo.builder()
				.title("blogA")
				.thumbnail("blogA 썸네일")
				.title("blogA 제목")
				.contents("blogA 내용")
				.date("2018-01-01")
				.tag("blogA 태그")
				.hits(1)
				.files(new ArrayList<BoardFileVo>())
				.images(new ArrayList<BoardImageVo>())
				.enabled(true)
				.build();
		
		sampleBlogB = BlogVo.builder()
				.title("blogB")
				.thumbnail("blogB 썸네일")
				.title("blogB 제목")
				.contents("blogB 내용")
				.date("2018-01-02")
				.tag("blogB 태그")
				.hits(2)
				.files(new ArrayList<BoardFileVo>())
				.images(new ArrayList<BoardImageVo>())
				.enabled(true)
				.build();
		
		sampleBlogC = BlogVo.builder()
				.title("blogC")
				.thumbnail("blogC 썸네일")
				.title("blogㅊ 제목")
				.contents("blogC 내용")
				.date("2018-01-02")
				.tag("blogC 태그")
				.hits(3)
				.files(new ArrayList<BoardFileVo>())
				.images(new ArrayList<BoardImageVo>())
				.enabled(true)
				.build();
	}
	
	
	@Test
	@Rollback(true)
	public void testGet() {
		int seq = blogDao.insert(sampleBlogA);
		
		BoardFileVo sampleFileA = BoardFileVo.builder()
				.boardSeq(seq)
				.pathname("fileA_이름.PDF")
				.build();
		boardFileDao.insert(fileTB, sampleFileA);
		
		BoardImageVo sampleImageA = BoardImageVo.builder()
				.boardSeq(seq)
				.pathname("imageA_이름.JPG")
				.build();
		boardImageDao.insert(imageTB, sampleImageA);
		
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seq).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seq).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seq).build());
		
		//ACT
		BlogVo resultBlog = blogDao.get(seq);
		
		//ASSERT
		sampleFileA.setBoardSeq(0);
		sampleImageA.setBoardSeq(0);
		sampleBlogA.getFiles().add(sampleFileA);
		sampleBlogA.getImages().add(sampleImageA);
		sampleBlogA.setComtCnt(3);
		assertEquals(sampleBlogA, resultBlog);
	}
	
	@Test
	@Rollback(true)
	public void testGetTags() {
		sampleBlogA.setTag("tagA tagB");
		sampleBlogB.setTag("tagA tagB");
		sampleBlogC.setTag("tagC");
		
		blogDao.insert(sampleBlogA);
		blogDao.insert(sampleBlogB);
		blogDao.insert(sampleBlogC);
		
		List<String> tags = blogDao.getTags();

		assertEquals(2, tags.size());
		assertTrue(tags.contains("tagA tagB"));
		assertTrue(tags.contains("tagC"));
		
	}
	
	@Test
	@Rollback(true)
	public void testCount() {
		blogDao.insert(sampleBlogA);
		blogDao.insert(sampleBlogB);
		blogDao.insert(sampleBlogC);
		
		int count = blogDao.count(null);
		
		assertEquals(3, count);
	}
	
	@Test
	@Rollback(true)
	public void testCountWithEnabledTrue() {
		sampleBlogA.setEnabled(true);
		sampleBlogB.setEnabled(false);
		sampleBlogC.setEnabled(false);
		
		blogDao.insert(sampleBlogA);
		blogDao.insert(sampleBlogB);
		blogDao.insert(sampleBlogC);
		
		Map<String, Object> params = new HashMap<>();
		params.put("enabled", true);
		
		int count = blogDao.count(params);
		
		assertEquals(1, count);
	}
	
	@Test
	public void testUpdate() {
		int seq = blogDao.insert(sampleBlogA);
		sampleBlogB.setSeq(seq);
		
		//ACT
		blogDao.update(sampleBlogB);

		//ASSERT
		BlogVo resultBlog = blogDao.get(seq);
		assertEquals(sampleBlogB, resultBlog);
	}
	
	@Test
	public void testDelete() {
		int seq = blogDao.insert(sampleBlogA);
		
		//ACT
		blogDao.delete(seq);

		//ASSERT
		BlogVo resultBlog = blogDao.get(seq);
		assertEquals(null, resultBlog);
	}
	
	
	@Test
	public void testList() {
		blogDao.insert(sampleBlogA);
		blogDao.insert(sampleBlogB);
		blogDao.insert(sampleBlogC);
		
		//ACT
		List<BlogVo> resultBlogs = blogDao.list(null);
		
		//ASSERT
		assertEquals(3, resultBlogs.size());
	}
	
	@Test
	public void testListWithEnabledTrue() {
		sampleBlogA.setEnabled(true);
		sampleBlogB.setEnabled(false);
		sampleBlogC.setEnabled(false);
		
		blogDao.insert(sampleBlogA);
		blogDao.insert(sampleBlogB);
		blogDao.insert(sampleBlogC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("enabled", true);
		
		//ACT
		List<BlogVo> resultBlogs = blogDao.list(params);
		
		//ASSERT
		assertEquals(1, resultBlogs.size());
		assertEquals(sampleBlogA, resultBlogs.get(0));
	}
	
	@Test
	public void testListWithTagA() {
		sampleBlogA.setTag("tagA tagB");
		sampleBlogB.setTag("tagB");
		sampleBlogC.setTag("tagC");
		
		blogDao.insert(sampleBlogA);
		blogDao.insert(sampleBlogB);
		blogDao.insert(sampleBlogC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		List<String> tags = new ArrayList<>();
		tags.add("tagA");
		params.put("tags", tags);
		
		//ACT
		List<BlogVo> resultBlogs = blogDao.list(params);
		
		//ASSERT
		assertEquals(1, resultBlogs.size());
		assertEquals(sampleBlogA, resultBlogs.get(0));
	}
	
	@Test
	public void testListWithTagB() {
		sampleBlogA.setTag("tagA tagB");
		sampleBlogB.setTag("tagB");
		sampleBlogC.setTag("tagC");
		
		blogDao.insert(sampleBlogA);
		blogDao.insert(sampleBlogB);
		blogDao.insert(sampleBlogC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		List<String> tags = new ArrayList<>();
		tags.add("tagB");
		params.put("tags", tags);
		
		//ACT
		List<BlogVo> resultBlogs = blogDao.list(params);
		
		//ASSERT
		assertEquals(2, resultBlogs.size());
	}
	
	@Test
	public void testListWithPaging() {
		sampleBlogA.setDate("2019-01-03");
		sampleBlogB.setDate("2019-01-02");
		sampleBlogC.setDate("2019-01-01");
		
		blogDao.insert(sampleBlogA);
		blogDao.insert(sampleBlogB);
		blogDao.insert(sampleBlogC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startRow", 1);
		params.put("perPgLine", 2);
		
		//ACT
		List<BlogVo> resultBlogs = blogDao.list(params);
		
		//ASSERT
		assertEquals(2, resultBlogs.size());
		assertEquals(sampleBlogB, resultBlogs.get(0));
		assertEquals(sampleBlogC, resultBlogs.get(1));
	}
	
	@Test
	public void testListWithSortSeq() {
		blogDao.insert(sampleBlogA);
		blogDao.insert(sampleBlogC);
		blogDao.insert(sampleBlogB);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "seq");
		params.put("order", "ASC");
		
		//ACT
		List<BlogVo> resultBlogs = blogDao.list(params);
		
		//ASSERT
		assertEquals(sampleBlogA, resultBlogs.get(0));
		assertEquals(sampleBlogC, resultBlogs.get(1));
		assertEquals(sampleBlogB, resultBlogs.get(2));
	}
	
	@Test
	public void testListWithSortTitle() {
		sampleBlogA.setTitle("1");
		sampleBlogB.setTitle("3");
		sampleBlogC.setTitle("2");
		
		blogDao.insert(sampleBlogA);
		blogDao.insert(sampleBlogB);
		blogDao.insert(sampleBlogC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "title");
		params.put("order", "ASC");
		
		//ACT
		List<BlogVo> resultBlogs = blogDao.list(params);
		
		//ASSERT
		assertEquals(sampleBlogA, resultBlogs.get(0));
		assertEquals(sampleBlogC, resultBlogs.get(1));
		assertEquals(sampleBlogB, resultBlogs.get(2));
	}
	
	
	@Test
	public void testListWithSortHits() {
		sampleBlogA.setHits(1);
		sampleBlogB.setHits(3);
		sampleBlogC.setHits(2);
		
		blogDao.insert(sampleBlogA);
		blogDao.insert(sampleBlogB);
		blogDao.insert(sampleBlogC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "hits");
		params.put("order", "ASC");
		
		//ACT
		List<BlogVo> resultBlogs = blogDao.list(params);
		
		//ASSERT
		assertEquals(sampleBlogA, resultBlogs.get(0));
		assertEquals(sampleBlogC, resultBlogs.get(1));
		assertEquals(sampleBlogB, resultBlogs.get(2));
	}
	
	@Test
	public void testListWithSortDate() {
		sampleBlogA.setDate("2019-01-01");
		sampleBlogB.setDate("2019-01-03");
		sampleBlogC.setDate("2019-01-02");
		
		blogDao.insert(sampleBlogA);
		blogDao.insert(sampleBlogB);
		blogDao.insert(sampleBlogC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "date");
		params.put("order", "asc");
		
		//ACT
		List<BlogVo> resultBlogs = blogDao.list(params);
		
		//ASSERT
		assertEquals(sampleBlogA, resultBlogs.get(0));
		assertEquals(sampleBlogC, resultBlogs.get(1));
		assertEquals(sampleBlogB, resultBlogs.get(2));
	}
	
	@Test
	public void testListWithSortComtCnt() {
		int seqA = blogDao.insert(sampleBlogA);
		int seqB = blogDao.insert(sampleBlogB);
		int seqC = blogDao.insert(sampleBlogC);
		
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seqA).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seqB).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seqB).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seqB).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seqC).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seqC).build());
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "comtCnt");
		params.put("order", "ASC");
		
		//ACT
		List<BlogVo> resultBlogs = blogDao.list(params);
		
		//ASSERT
		sampleBlogA.setComtCnt(1);
		sampleBlogB.setComtCnt(3);
		sampleBlogC.setComtCnt(2);
		assertEquals(sampleBlogA, resultBlogs.get(0));
		assertEquals(sampleBlogC, resultBlogs.get(1));
		assertEquals(sampleBlogB, resultBlogs.get(2));
	}
}
