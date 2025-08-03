// src/components/ui/FilterDropdown.tsx
'use client';

import * as DropdownMenu from '@radix-ui/react-dropdown-menu';
import { ChevronDown } from 'lucide-react';
import React from 'react';
import { cn } from '@/lib/utils';

type DropdownOption = string | { 
  value: string; 
  color?: string;
  dotColor?: string;
};

interface FilterDropdownProps {
  label: string;
  options: DropdownOption[];
  onSelect?: (value: string) => void;
}

const FilterDropdown = ({ label, options, onSelect }: FilterDropdownProps) => {
  return (
    <DropdownMenu.Root>
      <DropdownMenu.Trigger asChild>
        <button className="flex items-center bg-white border border-gray-300 rounded-md px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50 ml-3 focus:outline-none focus:ring-2 focus:ring-blue-500">
          {label}
          <ChevronDown className="w-4 h-4 ml-2" />
        </button>
      </DropdownMenu.Trigger>

      <DropdownMenu.Portal>
        <DropdownMenu.Content
          className="bg-white p-2 mt-1 w-48 rounded-md shadow-lg border border-gray-200 z-50"
          sideOffset={5}
        >
          {options.map((option, index) => {
            const value = typeof option === 'string' ? option : option.value;
            const color = typeof option === 'string' ? undefined : option.color;
            const dotColor = typeof option === 'string' ? undefined : option.dotColor;

            return (
              <DropdownMenu.Item
                // SỬA LỖI Ở ĐÂY: Dùng giá trị value và index để tạo key duy nhất
                key={`${value}-${index}`}
                className={cn(
                  'px-3 py-2 text-sm rounded-md hover:bg-gray-100 focus:bg-blue-500 focus:text-white focus:outline-none cursor-pointer font-medium',
                  color
                )}
                onSelect={() => onSelect?.(value)}
              >
                <div className="flex items-center">
                  {dotColor && (
                    <span className={cn('w-2 h-2 rounded-full mr-2', dotColor)}></span>
                  )}
                  {value}
                </div>
              </DropdownMenu.Item>
            );
          })}
        </DropdownMenu.Content>
      </DropdownMenu.Portal>
    </DropdownMenu.Root>
  );
};

export default FilterDropdown;