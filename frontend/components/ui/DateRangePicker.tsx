// src/components/ui/DateRangePicker.tsx
'use client';

import * as React from 'react';
import { format, startOfWeek, endOfWeek, startOfMonth, endOfMonth, startOfYear, endOfYear, subDays } from 'date-fns';
import { Calendar as CalendarIcon } from 'lucide-react';
import { DateRange } from 'react-day-picker';

import { cn } from '@/lib/utils';
import { Button } from '@/components/ui/button';
import { Calendar } from '@/components/ui/calendar';
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover';

// Các lựa chọn nhanh
const presets = [
  { label: 'Today', range: { from: new Date(), to: new Date() } },
  { label: 'This week', range: { from: startOfWeek(new Date()), to: endOfWeek(new Date()) } },
  { label: 'This month', range: { from: startOfMonth(new Date()), to: endOfMonth(new Date()) } },
  { label: 'This year', range: { from: startOfYear(new Date()), to: endOfYear(new Date()) } },
  { label: 'Last 7 days', range: { from: subDays(new Date(), 6), to: new Date() } },
];

interface DateRangePickerProps extends React.HTMLAttributes<HTMLDivElement> {
  // Props để nhận và cập nhật khoảng ngày đã được xác nhận
  date: DateRange | undefined;
  setDate: (date: DateRange | undefined) => void;
}

export function DateRangePicker({
  className,
  date,
  setDate,
}: DateRangePickerProps) {
  const [popoverOpen, setPopoverOpen] = React.useState(false);
  // State tạm thời để lưu lựa chọn của người dùng trước khi nhấn "Apply"
  const [tempDate, setTempDate] = React.useState<DateRange | undefined>(date);

  // Khi popover mở, đồng bộ state tạm thời với state chính
  React.useEffect(() => {
    if (popoverOpen) {
      setTempDate(date);
    }
  }, [popoverOpen, date]);

  const handleApply = () => {
    setDate(tempDate); // Cập nhật state chính
    setPopoverOpen(false); // Đóng popover
  };

  const handleCancel = () => {
    setPopoverOpen(false); // Chỉ cần đóng popover
  };

  const handlePresetSelect = (range: DateRange) => {
    setTempDate(range);
  };

  return (
    <div className={cn('grid gap-2', className)}>
      <Popover open={popoverOpen} onOpenChange={setPopoverOpen}>
        <PopoverTrigger asChild>
          <Button
            id="date"
            variant={'outline'}
            className={cn(
              'w-[300px] justify-start text-left font-normal',
              !date && 'text-muted-foreground'
            )}
          >
            <CalendarIcon className="mr-2 h-4 w-4" />
            {date?.from ? (
              date.to ? (
                <>
                  {format(date.from, 'dd/MM/y')} - {format(date.to, 'dd/MM/y')}
                </>
              ) : (
                format(date.from, 'dd/MM/y')
              )
            ) : (
              <span>Created at</span>
            )}
          </Button>
        </PopoverTrigger>
        <PopoverContent className="w-auto p-0 flex" align="start">
          {/* Thanh bên chứa các lựa chọn nhanh */}
          <div className="flex flex-col space-y-2 border-r border-gray-200 p-3">
            {presets.map(({ label, range }) => (
              <Button
                key={label}
                variant="ghost"
                className="w-full justify-start text-left"
                onClick={() => handlePresetSelect(range)}
              >
                {label}
              </Button>
            ))}
          </div>
          
          {/* Lịch và các nút hành động */}
          <div className="flex flex-col p-3">
            <Calendar
              initialFocus
              mode="range"
              defaultMonth={tempDate?.from}
              selected={tempDate}
              onSelect={setTempDate}
              numberOfMonths={2}
            />
            <div className="flex justify-end items-center gap-2 pt-2 border-t border-gray-200">
                <Button variant="ghost" onClick={handleCancel}>Cancel</Button>
                <Button onClick={handleApply}>Apply</Button>
            </div>
          </div>
        </PopoverContent>
      </Popover>
    </div>
  );
}