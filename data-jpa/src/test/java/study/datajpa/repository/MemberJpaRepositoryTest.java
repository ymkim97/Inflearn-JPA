package study.datajpa.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import study.datajpa.entity.Member;

@Transactional
@SpringBootTest
class MemberJpaRepositoryTest {

	@Autowired
	MemberJpaRepository memberJpaRepository;

	@Test
	void testMember() {
		Member member = new Member("memberA");
		Member savedMember = memberJpaRepository.save(member);

		Member findMember = memberJpaRepository.find(savedMember.getId());

		assertThat(findMember.getId()).isEqualTo(member.getId());
		assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		assertThat(findMember).isEqualTo(member);
	}

	@Test
	void basicCRUD() {
		Member member1 = new Member("member1");
		Member member2 = new Member("member2");
		memberJpaRepository.save(member1);
		memberJpaRepository.save(member2);

		Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
		Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
		assertThat(findMember1).isEqualTo(member1);
		assertThat(findMember2).isEqualTo(member2);

		List<Member> all = memberJpaRepository.findAll();
		assertThat(all).hasSize(2);

		long count = memberJpaRepository.count();
		assertThat(count).isEqualTo(2);

		memberJpaRepository.delete(member1);
		memberJpaRepository.delete(member2);

		long deletedCount = memberJpaRepository.count();
		assertThat(deletedCount).isZero();
	}

	@Test
	void findByUsernameAndAgeGreaterThan() {
		Member member1 = new Member("AAA", 10);
		Member member2 = new Member("AAA", 20);
		memberJpaRepository.save(member1);
		memberJpaRepository.save(member2);

		List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

		assertThat(result.get(0).getUsername()).isEqualTo("AAA");
		assertThat(result.get(0).getAge()).isEqualTo(20);
		assertThat(result).hasSize(1);
	}

	@Test
	void paging() {
		memberJpaRepository.save(new Member("member1", 10));
		memberJpaRepository.save(new Member("member2", 10));
		memberJpaRepository.save(new Member("member3", 10));
		memberJpaRepository.save(new Member("member4", 10));
		memberJpaRepository.save(new Member("member5", 10));

		int age = 10;
		int offset = 0;
		int limit = 3;

		List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
		long totalCount = memberJpaRepository.totalCount(10);

		assertThat(members).hasSize(3);
		assertThat(totalCount).isEqualTo(5);
	}

	@Test
	void bulkUpdate() {
		memberJpaRepository.save(new Member("member1", 10));
		memberJpaRepository.save(new Member("member2", 19));
		memberJpaRepository.save(new Member("member3", 20));
		memberJpaRepository.save(new Member("member4", 21));
		memberJpaRepository.save(new Member("member5", 40));

		int resultCount = memberJpaRepository.bulkAgePlus(20);

		assertThat(resultCount).isEqualTo(3);
	}
}