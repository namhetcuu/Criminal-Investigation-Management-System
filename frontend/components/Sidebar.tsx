'use client';

import { FiHome, FiFileText, FiBriefcase, FiLogOut, FiX } from 'react-icons/fi';
import Image from 'next/image';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import clsx from 'clsx';

const navItems = [
  { label: 'Dashboard', icon: FiHome, href: '/dashboard' },
  { label: 'Reports', icon: FiFileText, href: '/dashboard/reports' },
  { label: 'Cases', icon: FiBriefcase, href: '/dashboard/cases' },
];

export default function Sidebar({
  isOpen,
  onClose,
}: {
  isOpen: boolean;
  onClose: () => void;
}) {
  const pathname = usePathname();

  return (
    <aside
      className={clsx(
        'fixed md:static top-0 left-0 z-40 w-64 bg-gray-800 text-white transition-transform duration-300',
        isOpen ? 'translate-x-0' : '-translate-x-full',
        'md:translate-x-0',
        'h-screen md:min-h-screen overflow-y-auto'
      )}
    >
      <div className="flex flex-col justify-between h-full">
        {/* Mobile Close Button */}
        <div>
          <div className="flex md:hidden justify-end p-4">
            <button onClick={onClose}>
              <FiX size={20} />
            </button>
          </div>

          {/* Header */}
          <div className="flex items-center p-4 border-b border-gray-700">
            <Image
              src="/images/anh1.png"
              alt="Avatar"
              width={40}
              height={40}
              className="rounded-full"
            />
            <span className="ml-3 font-semibold text-sm">KIỂM DUYỆT</span>
          </div>

          {/* Navigation */}
          <nav className="p-4">
            <ul>
              {navItems.map(({ label, icon: Icon, href }) => (
                <li key={label} className="mb-2">
                  <Link
                    href={href}
                    className={clsx(
                      'flex items-center p-2 rounded transition-all',
                      pathname === href
                        ? 'bg-blue-500 text-white'
                        : 'text-gray-300 hover:bg-gray-700'
                    )}
                    onClick={onClose}
                  >
                    <Icon className="mr-3" size={18} />
                    <span className="text-sm font-medium">{label}</span>
                  </Link>
                </li>
              ))}
            </ul>
          </nav>
        </div>

        {/* Logout at bottom */}
        <div className="p-4 border-t border-gray-700">
          <button className="flex items-center w-full p-2 bg-gray-700 hover:bg-gray-600 rounded">
            <FiLogOut className="mr-3" size={18} />
            <span className="text-sm font-medium">Logout</span>
          </button>
        </div>
      </div>
    </aside>
  );
}
