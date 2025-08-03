// src/components/ui/badge.tsx
interface BadgeProps {
  variant?: 'default' | 'destructive' | 'warning' | 'success' | 'outline';
  status?: 'Approved' | 'Pending' | 'Rejected' | 'Urgent' | 'Not Urgent';
  children: React.ReactNode;
}

export const Badge = ({ variant = 'default', status, children }: BadgeProps) => {
  const baseClasses = 'px-3 py-1 text-xs font-semibold rounded-full';
  const variantClasses = {
    default: 'bg-gray-100 text-gray-800',
    destructive: 'bg-red-100 text-red-800',
    warning: 'bg-yellow-100 text-yellow-800',
    success: 'bg-green-100 text-green-800',
    outline: 'border border-gray-300 bg-transparent',
  };
  const statusClasses = {
    Approved: 'bg-green-100 text-green-800',
    Pending: 'bg-yellow-100 text-yellow-800',
    Rejected: 'bg-red-100 text-red-800',
    Urgent: 'bg-orange-100 text-orange-800',
    'Not Urgent': 'bg-blue-100 text-blue-800',
  };

  const classes = status ? statusClasses[status] : variantClasses[variant];

  return <span className={`${baseClasses} ${classes}`}>{children || status}</span>;
};

// Giữ nguyên default export để không phá vỡ code cũ
export default Badge;