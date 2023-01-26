import {
  StyledOptionItem,
  activeExist,
  StyledOptionList,
  StyledSelectedLabel,
  StyledSelectbox,
  Dropdown,
} from './style';

import { useState } from 'react';
function DropDownR25({ items, width }) {
  const [active, setActive] = useState(true); // 리스트가 열려있는지 확인
  const [selected, setSelected] = useState(items[0]); // 선택된 값을 selected에 담아 컴포넌트 간에 공유
  // <DropDownR25 items={arr2} />
  // App.js에서 prop값을 list형태로 넘기면 dropdown 내용 설정가능
  // 드롭다운 아이템 속성값들은 list로 구성될테니까..
  return (
    <StyledSelectbox width={width}>
      {' '}
      <StyledSelectedLabel value={selected} onClick={() => setActive(!active)}>
        {' '}
        {selected}
        <Dropdown src="/assets/icons/chevron-down.png" alt="chevron-down" />
      </StyledSelectedLabel>
      <StyledOptionList active={active}>
        {' '}
        {items
          .filter((item) => item !== selected)
          .map((item) => (
            <StyledOptionItem
              key={item} // map을 쓰기 위해서는 해당 방식으로 key가 주어져야함.
              onClick={() => {
                setActive(false);
                setSelected(item);
              }}
            >
              {' '}
              {item}
            </StyledOptionItem>
          ))}
      </StyledOptionList>
    </StyledSelectbox>
  );
}

export default DropDownR25;
