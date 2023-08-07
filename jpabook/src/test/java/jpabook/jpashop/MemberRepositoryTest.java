package jpabook.jpashop;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Test
	@Transactional
	@DisplayName("")
	void testMember() {
	    // given
	    Member member = new Member();
		member.setUsername("memberA");

		// when
		Long savedId = memberRepository.save(member);
		Member findMember = memberRepository.find(savedId);

	    // then
		assertThat(findMember.getId()).isEqualTo(member.getId());
		assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		assertThat(findMember).isEqualTo(member);
	}
}