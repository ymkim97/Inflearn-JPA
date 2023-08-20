package jpabook.jpashop.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@SpringBootTest
@Transactional
class MemberServiceTest {

	@Autowired
	MemberService memberService;

	@Autowired
	MemberRepository memberRepository;

	@Test
	void 회원가입() {
	    // given
		Member member = new Member();
		member.setName("kim");

	    // when
		memberService.join(member);

	    // then
		Assertions.assertThat(member).isEqualTo(memberRepository.findOne(member.getId()));
	}

	@Test
	void 중복_회원_예외() throws Exception {
	    // given
		Member member1 = new Member();
		member1.setName("kim");

		Member member2 = new Member();
		member2.setName("kim");

	    // when & then
		memberService.join(member1);
		Assertions.assertThatThrownBy(() -> memberService.join(member2))
			.isInstanceOf(IllegalStateException.class);
	}
}