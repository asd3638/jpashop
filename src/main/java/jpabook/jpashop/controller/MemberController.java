package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.dto.MemberDto;
import jpabook.jpashop.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/jpashop/member", produces = MediaTypes.HAL_JSON_VALUE)
public class MemberController{
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MemberController(MemberRepository memberRepository, ModelMapper modelMapper) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }


    //조회 api를 여기 queryMembers 메소드 하나에서만 사용하는 것은 아닐거임
    //근데 entity를 직접 만지면
    //이런 다양한 api에 대한 요청이 올 때마다 entity를 수정해야하고 그건 불가능
    //그러니까 해당 메소드를 위한 입력 출력 데이터 오브젝트 클래스를 하나 만들어 (ex ) MemberDto) 서 그거 사용해야해
    //entity 에 @JsonIgnore이런거 쓴는거 생각해보면
    //외부 데이터가 entity 로 담겨오는 로직에서 entity에서 외부 데이터로 정보가 나가는 로직으로 변경이 되고
    //이건 맞지 않음 이렇게 코딩하면 안됨
    //그리고 양방향 연결관계에서 무한루프 빠질 수 있는 위험성도 있음 그냥 여러모로 entity controller에서 직접 꺼내오거나 뿌리면 안된다.
    @GetMapping
    public ResponseEntity queryMembers() {
        List<Member> members = memberRepository.findAll();

        List<MemberDto> memberDtos = members.stream().map(m -> modelMapper.map(m, MemberDto.class)).collect(Collectors.toList());

        return ResponseEntity.ok(memberDtos);
    }

    @PostMapping
    public ResponseEntity createMember(@RequestBody @Valid MemberDto memberDto) {
        List<Member> memberRepositoryByName = memberRepository.findByName(memberDto.getName());
        //jpa-data 가 return 을 entity 로 하는데 그럼 이걸 다시 dto로 바꿔서..?
        if (!memberRepositoryByName.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        memberRepository.save(modelMapper.map(memberDto, Member.class));
        return ResponseEntity.ok(memberDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity queryMember(@PathVariable Integer id) {
        MemberDto memberDto = modelMapper.map(memberRepository.getOne(id), MemberDto.class);
        return ResponseEntity.ok(memberDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateMember(@PathVariable Integer id, @RequestBody @Valid MemberDto memberDto) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Member beforeMember = optionalMember.get();
        beforeMember.setName(memberDto.getName());
        beforeMember.setAddress(memberDto.getAddress());
        memberRepository.save(beforeMember);

        MemberDto changedMemberDto = modelMapper.map(beforeMember, MemberDto.class);

        return ResponseEntity.ok(changedMemberDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMember(@PathVariable Integer id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        memberRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
