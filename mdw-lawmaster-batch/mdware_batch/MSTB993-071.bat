@echo off
rem =======================================
rem MSTB993-071 �C�j�V����PLU�p�t�@�C���쐬
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB993-071 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
