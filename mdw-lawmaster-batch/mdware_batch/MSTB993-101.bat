@echo off
rem =======================================
rem MSTB993-101 �C�j�V����PLU�p�e�[�u�� �o�b�N�A�b�v
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB993-101 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
