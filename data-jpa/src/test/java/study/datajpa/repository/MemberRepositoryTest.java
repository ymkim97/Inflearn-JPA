package study.datajpa.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	TeamRepository teamRepository;

	@Test
	void testMember() {
		Member member = new Member("memberA");
		Member savedMember = memberRepository.save(member);

		Member findMember = memberRepository.findById(savedMember.getId()).get();

		assertThat(findMember.getId()).isEqualTo(member.getId());
		assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		assertThat(findMember).isEqualTo(member);
	}

	@Test
	void basicCRUD() {
		Member member1 = new Member("member1");
		Member member2 = new Member("member2");
		memberRepository.save(member1);
		memberRepository.save(member2);

		Member findMember1 = memberRepository.findById(member1.getId()).get();
		Member findMember2 = memberRepository.findById(member2.getId()).get();
		assertThat(findMember1).isEqualTo(member1);
		assertThat(findMember2).isEqualTo(member2);

		List<Member> all = memberRepository.findAll();
		assertThat(all).hasSize(2);

		long count = memberRepository.count();
		assertThat(count).isEqualTo(2);

		memberRepository.delete(member1);
		memberRepository.delete(member2);

		long deletedCount = memberRepository.count();
		assertThat(deletedCount).isZero();
	}

	@Test
	void findByUsernameAndAgeGreaterThan() {
		Member member1 = new Member("AAA", 10);
		Member member2 = new Member("AAA", 20);
		memberRepository.save(member1);
		memberRepository.save(member2);

		List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

		assertThat(result.get(0).getUsername()).isEqualTo("AAA");
		assertThat(result.get(0).getAge()).isEqualTo(20);
		assertThat(result).hasSize(1);
	}

	@Test
	void testQuery() {
		Member member1 = new Member("AAA", 10);
		Member member2 = new Member("AAA", 20);
		memberRepository.save(member1);
		memberRepository.save(member2);

		List<Member> result = memberRepository.findUser("AAA", 10);

		assertThat(result).hasSize(1);
	}

	@Test
	void findUsernameList() {
		Member member1 = new Member("AAA", 10);
		Member member2 = new Member("AAA", 20);
		memberRepository.save(member1);
		memberRepository.save(member2);

		List<String> usernameList = memberRepository.findUsernameList();
		for (String s : usernameList) {
			System.out.println("s = " + s);
		}
	}

	@Test
	void findMemberDto() {
		Team team = new Team("teamA");
		teamRepository.save(team);

		Member member1 = new Member("AAA", 10);
		member1.setTeam(team);
		memberRepository.save(member1);

		List<MemberDto> memberDto = memberRepository.findMemberDto();
		for (MemberDto dto : memberDto) {
			System.out.println("dto = " + dto);
		}
	}

	@Test
	void findByNames() {
		Member member1 = new Member("AAA", 10);
		Member member2 = new Member("AAA", 20);
		memberRepository.save(member1);
		memberRepository.save(member2);

		List<Member> byNames = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
		for (Member byName : byNames) {
			System.out.println("member = " + byName);
		}
	}

	@Test
	void returnType() {
		Member member1 = new Member("AAA", 10);
		Member member2 = new Member("BBB", 20);
		memberRepository.save(member1);
		memberRepository.save(member2);

		Member aaa = memberRepository.findMemberByUsername("AAA");
	}
}