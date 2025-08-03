"use client"

import * as React from "react"
import { CalendarIcon } from "lucide-react"
import { format } from "date-fns"

import { cn } from "@/lib/utils"
import { Button } from "@/components/ui/button"
import { Calendar } from "@/components/ui/calendar"
import { Input } from "@/components/ui/input"
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover"

interface DatetimePickerProps {
  value?: Date
  onChange?: (date: Date | undefined) => void
  placeholder?: string
  className?: string
}

export function DatetimePicker({
  value,
  onChange,
  placeholder = "Choose",
  className
}: DatetimePickerProps) {
  const [date, setDate] = React.useState<Date | undefined>(value)
  const [time, setTime] = React.useState("12:00")
  const [period, setPeriod] = React.useState<"AM" | "PM">("AM")

  const handleDateSelect = (selectedDate: Date | undefined) => {
    if (selectedDate) {
      const [hours, minutes] = time.split(":")
      let hour24 = parseInt(hours)
      
      if (period === "PM" && hour24 !== 12) hour24 += 12
      if (period === "AM" && hour24 === 12) hour24 = 0
      
      selectedDate.setHours(hour24, parseInt(minutes), 0, 0)
      setDate(selectedDate)
      onChange?.(selectedDate)
    }
  }

  const handleTimeChange = (newTime: string) => {
    setTime(newTime)
    if (date) {
      const [hours, minutes] = newTime.split(":")
      let hour24 = parseInt(hours)
      
      if (period === "PM" && hour24 !== 12) hour24 += 12
      if (period === "AM" && hour24 === 12) hour24 = 0
      
      const newDate = new Date(date)
      newDate.setHours(hour24, parseInt(minutes), 0, 0)
      setDate(newDate)
      onChange?.(newDate)
    }
  }

  const formatDateTime = (date: Date) => {
    return format(date, "MMM d, yyyy") + " " + time + " " + period
  }

  return (
    <Popover>
      <PopoverTrigger asChild>
        <Button
          variant="outline"
          className={cn(
            "justify-start text-left font-normal bg-blue-100 hover:bg-blue-200 border-blue-200",
            !date && "text-muted-foreground",
            className
          )}
        >
          <CalendarIcon className="mr-2 h-4 w-4" />
          {date ? formatDateTime(date) : placeholder}
        </Button>
      </PopoverTrigger>
      <PopoverContent className="w-auto p-0" align="start">
        <div className="bg-white rounded-lg shadow-lg border">
          {/* Calendar Section */}
          <Calendar
            mode="single"
            selected={date}
            onSelect={handleDateSelect}
            initialFocus
            className="p-3"
            classNames={{
              day_selected: "bg-blue-600 text-white hover:bg-blue-600 hover:text-white focus:bg-blue-600 focus:text-white rounded-full",
              day_today: "bg-gray-200 text-gray-900 rounded-full",
              day: "hover:bg-gray-100 rounded-full transition-colors",
              head_cell: "text-gray-500 font-normal text-sm",
              caption_label: "text-blue-600 font-medium",
              nav_button: "text-blue-600 hover:bg-blue-100 rounded-full",
            }}
          />
          
          {/* Time Picker Section */}
          <div className="border-t bg-white p-4">
            <div className="space-y-3">
              <label className="text-sm font-medium text-gray-700">Time</label>
              <div className="flex items-center space-x-3">
                {/* Time Input */}
                <Input
                  value={time}
                  onChange={(e) => handleTimeChange(e.target.value)}
                  className="w-20 text-center font-mono text-lg"
                  placeholder="HH:MM"
                  type="text"
                  inputMode="numeric"
                  pattern="[0-9]{2}:[0-9]{2}"
                  maxLength={5}
                />
                
                {/* AM/PM Toggle */}
                <div className="flex rounded-lg border bg-gray-100 p-1">
                  <Button
                    type="button"
                    variant="ghost"
                    size="sm"
                    className={cn(
                      "h-8 px-3 text-sm font-medium transition-all",
                      period === "AM" 
                        ? "bg-white text-gray-900 shadow-sm" 
                        : "text-gray-600 hover:text-gray-900"
                    )}
                    onClick={() => setPeriod("AM")}
                  >
                    AM
                  </Button>
                  <Button
                    type="button"
                    variant="ghost"
                    size="sm"
                    className={cn(
                      "h-8 px-3 text-sm font-medium transition-all",
                      period === "PM" 
                        ? "bg-white text-gray-900 shadow-sm" 
                        : "text-gray-600 hover:text-gray-900"
                    )}
                    onClick={() => setPeriod("PM")}
                  >
                    PM
                  </Button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </PopoverContent>
    </Popover>
  )
}
